package com.htz.tmall.web.filter;

import com.htz.tmall.pojo.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class ForeAuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String contextPath = request.getContextPath();

        String[] noNeedAuthPage = new String[]{
                "homepage",
                "checkLogin",
                "register",
                "login",
                "product",
                "category",
                "search",
                "existUser"};
        String uri = request.getServletPath();
        if(uri.startsWith("/fore") && !uri.startsWith("/foreServlet")){
            String method = uri.substring(5);
            if(!Arrays.asList(noNeedAuthPage).contains(method)){
                User user = (User) request.getSession().getAttribute("user");
                if(null == user){
                    response.sendRedirect(contextPath + "/login.jsp");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
