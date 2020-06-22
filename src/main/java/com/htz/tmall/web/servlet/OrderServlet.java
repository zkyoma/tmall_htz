package com.htz.tmall.web.servlet;

import com.htz.tmall.dao.OrderDao;
import com.htz.tmall.pojo.Order;
import com.htz.tmall.service.OrderService;
import com.htz.tmall.service.impl.OrderServiceImpl;
import com.htz.tmall.utils.Page;
import com.htz.tmall.utils.PageUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet("/orderServlet")
public class OrderServlet extends BaseBackServlet {
    private OrderService orderService = new OrderServiceImpl();

    public String list(HttpServletRequest request){
        //1. 获取参数
        int pageNo = PageUtils.checkPageNo(request.getParameter("pageNo"));
        int pageSize = PageUtils.checkPageSize(request.getParameter("pageSize"));
        //2. 获取order对应的page对象
        Page<Order> page = orderService.getPage(pageNo, pageSize);
        //3. 放入request域中
        request.setAttribute("page", page);
        return "admin/listOrder.jsp";
    }

    public String delivery(HttpServletRequest request) {
        //1. 获取订单的id
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        //2. 查询id对应的订单
        orderService.update(id, OrderDao.waitConfirm);
        return "@admin_order_list";
    }
}
