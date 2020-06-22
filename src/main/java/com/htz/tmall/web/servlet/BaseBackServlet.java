package com.htz.tmall.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseBackServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = (String) request.getAttribute("method");
        try {
            Method method = getClass().getMethod(methodName, HttpServletRequest.class);
            String returnValue = (String) method.invoke(this, request);
            if(returnValue.startsWith("@")){
                //重定向
                response.sendRedirect(request.getContextPath() + "/" + returnValue.substring(1));
            }else if(returnValue.startsWith("%")){
                response.getWriter().write(returnValue.substring(1));
            }else {
                request.getRequestDispatcher("/" + returnValue).forward(request, response);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            //e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/errorBase.jsp");
            throw new RuntimeException(e);
        }
    }
}
