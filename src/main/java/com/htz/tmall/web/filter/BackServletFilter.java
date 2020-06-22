package com.htz.tmall.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BackServletFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //admin_category_list
        String uri = request.getServletPath();
        if(uri.startsWith("/admin_")){
            //categoryServlet
            String servletPath = uri.substring(uri.indexOf("_") + 1, uri.lastIndexOf("_")) + "Servlet";
            //list
            String method = uri.substring(uri.lastIndexOf("_") + 1);
            request.setAttribute("method", method);
            request.getRequestDispatcher("/" + servletPath).forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
