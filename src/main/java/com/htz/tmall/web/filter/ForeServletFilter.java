package com.htz.tmall.web.filter;

import com.htz.tmall.dao.impl.CategoryDaoImpl;
import com.htz.tmall.pojo.Category;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ForeServletFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String contextPath=request.getContextPath();
        request.getServletContext().setAttribute("contextPath", contextPath);

        List<Category> cs = (List<Category>) request.getAttribute("cs");
        if(null == cs){
            cs = new CategoryDaoImpl().findAll();
            request.setAttribute("cs", cs);
        }

        String servletPath = request.getServletPath();
        if(servletPath.startsWith("/fore") && !servletPath.startsWith("/foreServlet")){
            String method = servletPath.substring(5);
            request.setAttribute("method", method);
            request.getRequestDispatcher("/foreServlet").forward(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
