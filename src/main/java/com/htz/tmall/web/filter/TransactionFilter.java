package com.htz.tmall.web.filter;

import com.htz.tmall.utils.ConnectionContext;
import com.htz.tmall.utils.JDBCUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        Connection conn = null;
        try {
            //1. 获取连接
            conn = JDBCUtils.getConnection();
            //2. 设置手动提交
            conn.setAutoCommit(false);
            //3. 利用ThreadLocal把当前线程和连接绑定
            ConnectionContext.getInstance().bind(conn);
            //4. 把请求转给目标servlet
            chain.doFilter(request, response);
            //5. 提交事务
            conn.commit();
        } catch (Exception e) {
            //e.printStackTrace();
            //6. 回滚事务
            if(null != conn) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            //7. 解除绑定
            ConnectionContext.getInstance().remove();
            //8. 关闭连接
            JDBCUtils.release(conn);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
