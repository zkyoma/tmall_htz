package com.htz.tmall.web.servlet;

import com.htz.tmall.pojo.Category;
import com.htz.tmall.pojo.Product;
import com.htz.tmall.service.CategoryService;
import com.htz.tmall.service.ProductService;
import com.htz.tmall.service.impl.CategoryServiceImpl;
import com.htz.tmall.service.impl.ProductServiceImpl;
import com.htz.tmall.utils.ImageUtil;
import com.htz.tmall.utils.Page;
import com.htz.tmall.utils.PageUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/productServlet")
@SuppressWarnings("all")
public class ProductServlet extends BaseBackServlet {
    private ProductService productService = new ProductServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();

    public String list(HttpServletRequest request){
        //1. 获取参数
        //当前页
        int pageNo = PageUtils.checkPageNo(request.getParameter("pageNo"));
        //每页记录数
        int pageSize = PageUtils.checkPageSize(request.getParameter("pageSize"));
        //分类对应的cid
        String cidStr = request.getParameter("cid");
        int cid = 0;
        try {
            cid = Integer.parseInt(cidStr);
        } catch (NumberFormatException ignore) {
        }
        if(cid == 0){
            return "@errorBase.jsp";
        }
        //2. 获取category对象
        Category c = categoryService.get(cid);
        if(c == null){
            return "@errorBase.jsp";
        }
        //3. 获取该页对应的page对象
        Page<Product> page = productService.getPage(pageNo, pageSize, cid);
        page.setParam("&cid=" + cid);
        //4. 存入request域中
        request.setAttribute("page", page);
        request.setAttribute("c", c);
        //5. 转发到listProduct.jsp
        return "admin/listProduct.jsp";
    }

    public String add(HttpServletRequest request){
        //1. 获取参数
        //cid
        int cid = Integer.parseInt(request.getParameter("cid"));
        //product对应的属性信息
        String name = request.getParameter("name");
        String subTitle = request.getParameter("subTitle");

        String originalPriceStr = request.getParameter("originalPrice");
        String promotePriceStr = request.getParameter("promotePrice");
        String stockStr = request.getParameter("stock");

        float originalPrice = 0;
        try {
            originalPrice = Float.parseFloat(originalPriceStr);
        } catch (NumberFormatException ignore) {
        }
        float promotePrice = 0;
        try {
            promotePrice = Float.parseFloat(promotePriceStr);
        } catch (NumberFormatException ignore) {
        }
        int stock = 0;
        try {
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException ignore) {
        }
        //2. 创建product对象
        Product product = new Product();
        product.setName(name);
        product.setCreateDate(new Date());
        product.setSubTitle(subTitle);
        product.setOriginalPrice(originalPrice);
        product.setPromotePrice(promotePrice);
        product.setStock(stock);
        product.setCategory(categoryService.get(cid));
        //3. 保存
        productService.add(product);
        return "@admin_product_list?cid=" + cid;
    }

    public String delete(HttpServletRequest request){
        //1. 获取产品的id
        int id = Integer.parseInt(request.getParameter("id"));
        int cid = Integer.parseInt(request.getParameter("cid"));
        //2. 删除product以及对应的图片
        //图片路径集合
        Map<String, List<String>> imgFilePathMap = ImageUtil.fillImageFileMap(request);
        productService.deleteProductAndImagesAndPvs(id, imgFilePathMap);
        //3. 重定向
        return "@admin_product_list?cid=" + cid;
    }

    public String editPropertyValue(HttpServletRequest request){
        //1. 获取product的id
        String idStr = request.getParameter("id");
        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ignore) {
            return "@errorBase.jsp";
        }
        //2. 获取product，并填充属性，分类
        Product product = productService.get(id, true);
        if(product == null){
            return "@errorBase.jsp";
        }
        //3. 存入request域中
        request.setAttribute("p", product);
        return "admin/editProductValue.jsp";
    }

    public String updatePropertyValue(HttpServletRequest request){
        //1. 获取propertyValue的pvid
        String pvidStr = request.getParameter("pvid");
        int pvid = Integer.parseInt(pvidStr);
        //2. 获取value
        String value = request.getParameter("value");
        productService.updatePropertyValue(pvid, value);
        return "%success";
    }

    public String edit(HttpServletRequest request){
        //1. 获取product对应的pid
        String pidStr = request.getParameter("id");
        int pid = 0;
        try {
            pid = Integer.parseInt(pidStr);
        } catch (NumberFormatException ignore) {
            return "@errorBase.jsp";
        }
        //2. 获取Product
        Product product = productService.get(pid, false);
        if(product == null){
            return "@errorBase.jsp";
        }
        //3. 放入request域中
        request.setAttribute("p", product);
        return "admin/editProduct.jsp";
    }

    public String update(HttpServletRequest request){
        //1. 获取product对应的pid
        String pidStr = request.getParameter("id");
        int pid = Integer.parseInt(pidStr);
        //cid
        int cid = Integer.parseInt(request.getParameter("cid"));
        //2. 获取其他参数
        String name = request.getParameter("name");
        String subTitle = request.getParameter("subTitle");
        String originalPriceStr = request.getParameter("originalPrice");
        String promotePriceStr = request.getParameter("promotePrice");
        String stockStr = request.getParameter("stock");

        float originalPrice = 0;
        try {
            originalPrice = Float.parseFloat(originalPriceStr);
        } catch (NumberFormatException ignore) {
        }
        float promotePrice = 0;
        try {
            promotePrice = Float.parseFloat(promotePriceStr);
        } catch (NumberFormatException ignore) {
        }
        int stock = 0;
        try {
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException ignore) {
        }
        //3. 创建product对象
        Product product = new Product();
        product.setId(pid);
        product.setName(name);
        product.setCreateDate(new Date());
        product.setSubTitle(subTitle);
        product.setOriginalPrice(originalPrice);
        product.setPromotePrice(promotePrice);
        product.setStock(stock);
        product.setCid(cid);
        //3. 更新
        productService.updateProduct(product);
        return "@admin_product_list?cid=" + cid;
    }
}
