package com.htz.tmall.web.servlet;

import com.htz.tmall.dao.OrderDao;
import com.htz.tmall.pojo.*;
import com.htz.tmall.service.*;
import com.htz.tmall.service.impl.*;
import com.htz.tmall.utils.ResultInfo;
import org.apache.commons.lang.math.RandomUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@WebServlet("/foreServlet")
public class ForeServlet extends BaseForeServlet {
    private CategoryService categoryService = new CategoryServiceImpl();
    private UserService userService = new UserServiceImpl();
    private ProductService productService = new ProductServiceImpl();
    private OrderService orderService = new OrderServiceImpl();
    private OrderItemService orderItemService = new OrderItemServiceImpl();

    /**
     * 首页
     * @param request
     * @return
     */
    public String home(HttpServletRequest request, HttpServletResponse response) {
        //1. 查询所有category，并封装对应的product集合
        List<Category> cs = categoryService.queryAll();
        request.setAttribute("cs", cs);
        return "home.jsp";
    }

    /**
     * 检验用户名是否已经存在
     */
    public void existUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1. 获取用户名
        String name = request.getParameter("name");
        boolean flag = userService.isExistUser(name);
        ResultInfo info = new ResultInfo();
        info.setFlag(flag);
        if(flag){
            info.setMsg("用户名已经存在，请更换");
        }
        writeValue(response, info);
    }

    /**
     * 注册
     * @param request
     * @return
     */
    public String register(HttpServletRequest request, HttpServletResponse response) {
        //1. 获取基本信息
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        //2. 创建user对象
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        //3. 保存
        userService.add(user);
        return "@registerSuccess.jsp";
    }

    /**
     * 登陆
     * @param request
     * @param response
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1. 获取参数
        //名称
        String name = request.getParameter("name");
        //密码
        String password = request.getParameter("password");
        //2. 查询
        User user = userService.getByNameAndPwd(name, password);
        ResultInfo info = new ResultInfo();
        if (user == null) {
            info.setFlag(false);
            info.setMsg("用户名或密码错误");
            writeValue(response, info);
            return;
        }
        request.getSession().setAttribute("user", user);
        //3. 登陆成功后，查询购物车的orderItems集合
        ShoppingCart shoppingCart = new ShoppingCart();
        List<OrderItem> ois = orderItemService.getByUid(user.getId());
        int cartTotalItemNumber = 0;
        for(OrderItem oi : ois){
            cartTotalItemNumber += oi.getNumber();
        }
        shoppingCart.setOrderItems(ois);
        shoppingCart.setCartTotalItemNumber(cartTotalItemNumber);
        //把购物车放入session中
        request.getSession().setAttribute("cart", shoppingCart);
        info.setFlag(true);
        writeValue(response, info);
    }

    /**
     * 退出
     * @param request
     * @param response
     * @return
     */
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return "@login.jsp";
    }

    /**
     * 产品页
     * @param request
     * @param response
     * @return
     */
    public String product(HttpServletRequest request, HttpServletResponse response) {
        //1. 获取产品的pid
        String pidStr = request.getParameter("pid");
        int pid = 0;
        try {
            pid = Integer.parseInt(pidStr);
        } catch (NumberFormatException e) {
            return "@errorFore.jsp";
        }
        //2. 查询对应的product对象
        Product product = productService.getAndFillAll(pid);
        if (product == null) {
            return "@errorFore.jsp";
        }
        //3. 放入request域中
        request.setAttribute("p", product);
        return "product.jsp";
    }

    /**
     * 立即购买或加入购物车时，判断用户是否登陆
     * @param request
     * @param response
     */
    public void checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取session中的user
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            ResultInfo info = new ResultInfo();
            info.setFlag(true);
            writeValue(response, info);
        }
    }

    /**
     * 分类页
     * @param request
     * @param response
     * @return
     */
    public String category(HttpServletRequest request, HttpServletResponse response) {
        //1. 获取category的cid
        String cidStr = request.getParameter("cid");
        int cid = 0;
        try {
            cid = Integer.parseInt(cidStr);
        } catch (NumberFormatException e) {
            return "@errorFore.jsp";
        }
        //2. 获取category对象，并填充对应Products，而且为product设置saleCount，reviewCount
        Category category = categoryService.getWithProducts(cid);
        if(category == null){
            return "@errorFore.jsp";
        }
        List<Product> products = category.getProducts();
        //3. 获取sort的值
        String sort = request.getParameter("sort");
        if (sort != null) {
            switch (sort) {
                case "review":
                    products.sort((p1, p2) -> p2.getReviewCount() - p1.getReviewCount());
                    break;
                case "saleCount":
                    products.sort((p1, p2) -> p2.getSaleCount() - p1.getSaleCount());
                    break;
                case "date":
                    products.sort((p1, p2) -> p1.getCreateDate().compareTo(p2.getCreateDate()));
                    break;
                case "price":
                    products.sort((p1, p2) -> (int) (p2.getPromotePrice() - p1.getPromotePrice()));
                    break;
                case "all":
                    products.sort((p1, p2) -> p2.getSaleCount() * p2.getReviewCount() - p1.getSaleCount() * p1.getReviewCount());
                    break;
            }
        }
        //4. 放入request域中
        request.setAttribute("c", category);
        return "category.jsp";
    }

    /**
     * 搜素
     * @param request
     * @param response
     * @return
     */
    public String search(HttpServletRequest request, HttpServletResponse response){
        //1. 获取搜索的keyword
        String keyword = request.getParameter("keyword");
        //2. 查询符合条件的products集合
        List<Product> products = productService.search(keyword);
        //3. 放入request域中
        request.setAttribute("ps", products);
        return "searchResult.jsp";
    }

    /**
     * 立即购买
     * @param request
     * @return
     */
    public String buyNow(HttpServletRequest request, HttpServletResponse response){
        //1. 获取product的pid
        int pid = Integer.parseInt(request.getParameter("pid"));
        //2. 获取购买数量
        int num = Integer.parseInt(request.getParameter("num"));
        //3. 查询product，设置firstProductImage
        Product product = productService.getProAndImg(pid);
        //4. 创建orderItem对象
        //获取user
        User user = (User) request.getSession().getAttribute("user");
        OrderItem orderItem = new OrderItem();
        orderItem.setNumber(num);
        orderItem.setPid(product.getId());
        orderItem.setUid(user.getId());
        orderItem.setProduct(product);
        List<OrderItem> ois = new ArrayList<>();
        ois.add(orderItem);
        //5. 放入request域中
        request.getSession().setAttribute("ois", ois);
        request.getSession().setAttribute("buyNow", true);
        float total = product.getPromotePrice() * orderItem.getNumber();
        request.setAttribute("total", total);
        return "buy.jsp";
    }

    /**
     * 添加购物车
     * @param request
     * @param response
     */
    public void addCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1. 获取参数
        //product的pid
        String pidStr = request.getParameter("pid");
        int pid = Integer.parseInt(pidStr);
        //num
        String numStr = request.getParameter("num");
        int num = Integer.parseInt(numStr);
        //2. 获取user
        User user = (User) request.getSession().getAttribute("user");
        //3. 判断购物车是否有此product，若没有，创建orderItem对象，保存到数据库；若有，更新product数量
        ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");
        orderItemService.addCart(pid, num, user.getId(), cart);
        ResultInfo info = new ResultInfo();
        info.setFlag(true);
        writeValue(response, info);
    }

    /**
     * 购物车页面显示
     * @param request
     * @param response
     * @return
     */
    public String cart(HttpServletRequest request, HttpServletResponse response){
        return "cart.jsp";
    }

    /**
     * 购物车orderItem数量变化
     * @param request
     * @param response
     */
    public void changeOrderItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取user
        User user = (User) request.getSession().getAttribute("user");
        if(user != null){
            //1. 获取参数
            //oiid
            int oiid = Integer.parseInt(request.getParameter("oiid"));
            //num
            int num = Integer.parseInt(request.getParameter("number"));
            //2. 更新该oiid对应orderItem的数量
            orderItemService.changeOrderItemNum(oiid, num);
            //更新购物车对应的数量
            ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");
            for(OrderItem oi : cart.getOrderItems()){
                if(oi.getId() == oiid){
                    cart.setCartTotalItemNumber(cart.getCartTotalItemNumber() - oi.getNumber() + num);
                    oi.setNumber(num);
                    break;
                }
            }
            ResultInfo info = new ResultInfo();
            info.setFlag(true);
            writeValue(response, info);
        }
    }

    /**
     * 删除订单项
     * @param request
     * @param response
     */
    public void deleteOrderItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        if(user != null) {
            //1. 获取orderItem的id
            int oiid = Integer.parseInt(request.getParameter("oiid"));
            //2. 删除
            orderItemService.deleteById(oiid);
            //更新购物车
            ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");
            Iterator<OrderItem> iterator = cart.getOrderItems().iterator();
            while (iterator.hasNext()){
                OrderItem oi = iterator.next();
                if(oi.getId() == oiid){
                    cart.setCartTotalItemNumber(cart.getCartTotalItemNumber() - oi.getNumber());
                    iterator.remove();
                    break;
                }
            }
            ResultInfo info = new ResultInfo();
            info.setFlag(true);
            writeValue(response, info);
        }
    }

    /**
     * 购物车结算
     * @param request
     * @return
     */
    public String buy(HttpServletRequest request, HttpServletResponse response){
        //1. 获取orderItem的id集合
        String[] oiids = request.getParameterValues("oiid");
        List<OrderItem> ois = new ArrayList<>();
        //2. 查询对应的订单项集合，计算并返回总金额
        float total = orderItemService.getOisByIds(oiids, ois);
        //3. 放入request域中
        request.getSession().setAttribute("ois", ois);
        request.setAttribute("total", total);
        return "buy.jsp";
    }

    /**
     * 生成订单
     * @param request
     * @return
     */
    public String createOrder(HttpServletRequest request, HttpServletResponse response){
        //获取User
        User user = (User) request.getSession().getAttribute("user");
        //1. 获取订单项集合
        List<OrderItem> ois = (List<OrderItem>) request.getSession().getAttribute("ois");
        boolean buyNow = (boolean) request.getSession().getAttribute("buyNow");
        if(user == null || ois.isEmpty()){
            return "@login.jsp";
        }
        //2. 获取其他参数
        String address = request.getParameter("address");
        String post = request.getParameter("post");
        String receiver = request.getParameter("receiver");
        String mobile = request.getParameter("mobile");
        String userMessage = request.getParameter("userMessage");
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);

        Order order = new Order();

        order.setOrderCode(orderCode);
        order.setAddress(address);
        order.setPost(post);
        order.setReceiver(receiver);
        order.setMobile(mobile);
        order.setUserMessage(userMessage);
        order.setCreateDate(new Date());
        order.setUser(user);
        order.setStatus(OrderDao.waitPay);

        //3. 保存订单，并更新订单项，计算总金额并返回
        float total = orderService.addOrderAndUpdateOderItem(order, ois, buyNow);
        request.setAttribute("total", total);
        request.setAttribute("oid", order.getId());

        return "@forealipay?oid="+order.getId() +"&total="+total;
    }

    /**
     * 转发到alipay.jsp，避免重复提交表单
     * @param request
     * @param response
     * @return
     */
    public String alipay(HttpServletRequest request, HttpServletResponse response){
        return "alipay.jsp";
    }

    /**
     * 支付
     * @param request
     * @param response
     * @return
     */
    public String payed(HttpServletRequest request, HttpServletResponse response){
        //1. 获取参数
        int oid = Integer.parseInt(request.getParameter("oid"));
        //2. 更新order状态
        Order order = orderService.update(oid, OrderDao.waitDelivery);
        request.setAttribute("o", order);
        return "payed.jsp";
    }

    /**
     * 我的订单
     * @param request
     * @param response
     * @return
     */
    public String bought(HttpServletRequest request, HttpServletResponse response){
        //1. 获取user
        User user = (User) request.getSession().getAttribute("user");
        //2. 查询user未删除的订单
        List<Order> orders = orderService.getOrderByUidWithoutDelete(user.getId());
        request.setAttribute("os", orders);
        return "bought.jsp";
    }

    /**
     * 确认收货页面
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String confirmPay(HttpServletRequest request, HttpServletResponse response) {
        int oid = Integer.parseInt(request.getParameter("oid"));
        Order o = orderService.getByOidWithOis(oid);
        request.setAttribute("o", o);
        return "confirmPay.jsp";
    }

    /**
     * 确认收获
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String orderConfirmed(HttpServletRequest request, HttpServletResponse response) {
        //1. 获取oid
        int oid = Integer.parseInt(request.getParameter("oid"));
        //2. 更新order状态
        orderService.update(oid, OrderDao.waitReview);
        return "orderConfirmed.jsp";
    }

    /**
     * 删除订单
     * @param request
     * @param response
     * @param page
     * @return
     */
    public void deleteOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int oid = Integer.parseInt(request.getParameter("oid"));
        orderService.update(oid, OrderDao.delete);
        ResultInfo info = new ResultInfo();
        info.setFlag(true);
        writeValue(response, info);
    }

    /**
     * 评价页面
     *
     * @param request
     * @param response
     * @param page
     * @return
     */
    public String review(HttpServletRequest request, HttpServletResponse response) {
        //1. 获取order的oid
        int oid = Integer.parseInt(request.getParameter("oid"));
        //2. 根据oid查询订单
        //2.1 填充订单项
        //2.2 为订单项填充产品
        //2.3 为产品填充firstProductImage，saleCount，reviewCount，reviews集合，
        Order order = orderService.getByOidWithOisAndReviews(oid);
        request.setAttribute("o", order);
        request.setAttribute("p", order.getOrderItems().get(0).getProduct());
        return "review.jsp";
    }

    public String doreview(HttpServletRequest request, HttpServletResponse response){
        //1. 获取参数
        //oid
        int oid = Integer.parseInt(request.getParameter("oid"));
        //获取产品的pid
        int pid = Integer.parseInt(request.getParameter("pid"));
        //评价内容
        String content = request.getParameter("content");
        //2. 获取User
        User user = (User) request.getSession().getAttribute("user");
        //3. 评价
        //修改订单status为finish
        //保存review
        orderService.review(oid, pid, user.getId(), content);
        return "@forereview?oid="+oid+"&showonly=true";
    }
}
