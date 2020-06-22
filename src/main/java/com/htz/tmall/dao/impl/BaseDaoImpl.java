package com.htz.tmall.dao.impl;

import com.htz.tmall.dao.BaseDao;
import com.htz.tmall.utils.ConnectionContext;
import com.htz.tmall.utils.JDBCUtils;
import com.htz.tmall.utils.ReflectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {
    private QueryRunner queryRunner = new QueryRunner();
    private Class<T> clazz = null;

    public BaseDaoImpl(){
        clazz = ReflectionUtils.getSuperGenericType(getClass());
    }

    @Override
    public Integer insert(String sql, Object... args) {
        int id = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConnectionContext.getInstance().get();
            ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            if(args != null){
                for(int i = 0; i < args.length; i++){
                    ps.setObject(i + 1, args[i]);
                }
            }
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.release(rs, ps);
        }
        return id;
    }

    @Override
    public void update(String sql, Object... args) {
        Connection conn = null;
        try {
            conn = ConnectionContext.getInstance().get();
            queryRunner.update(conn, sql, args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public T query(String sql, Object... args) {
        Connection conn = null;
        try {
            conn = ConnectionContext.getInstance().get();
            return queryRunner.query(conn, sql, new BeanHandler<>(clazz), args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> queryForList(String sql, Object... args) {
        Connection conn = null;
        try {
            conn = ConnectionContext.getInstance().get();
            return queryRunner.query(conn, sql, new BeanListHandler<>(clazz), args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public <V> V getSingleVal(String sql, Object... args) {
        Connection conn = null;
        try {
            conn = ConnectionContext.getInstance().get();
            return (V) queryRunner.query(conn, sql, new ScalarHandler(), args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void batch(String sql, Object[]... params) {
        Connection conn = null;
        try {
            conn = ConnectionContext.getInstance().get();
            queryRunner.batch(conn, sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
