package com.htz.tmall.web.servlet;

import com.htz.tmall.pojo.User;
import com.htz.tmall.service.UserService;
import com.htz.tmall.service.impl.UserServiceImpl;
import com.htz.tmall.utils.Page;
import com.htz.tmall.utils.PageUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

@WebServlet("/userServlet")
public class UserServlet extends BaseBackServlet {
    private UserService userService = new UserServiceImpl();

    public String list(HttpServletRequest request){
        //1. 获取参数
        int pageNo = PageUtils.checkPageNo(request.getParameter("pageNo"));
        int pageSize = PageUtils.checkPageSize(request.getParameter("pageSize"));
        //2. 获取user对应的page对象
        Page<User> page = userService.getPage(pageNo, pageSize);
        //3. 放入request域中
        request.setAttribute("page", page);
        return "admin/listUser.jsp";
    }
}
