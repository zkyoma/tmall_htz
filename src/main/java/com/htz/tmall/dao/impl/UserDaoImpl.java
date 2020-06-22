package com.htz.tmall.dao.impl;

import com.htz.tmall.dao.UserDao;
import com.htz.tmall.pojo.User;

import java.util.List;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    @Override
    public Long getTotalNumber() {
        String sql = "select count(id) from user";
        return getSingleVal(sql);
    }

    @Override
    public List<User> queryForPage(int start, int pageSize) {
        String sql = "select * from user limit ?, ?";
        return queryForList(sql, start, pageSize);
    }

    @Override
    public User getByUid(int uid) {
        String sql = "select * from user where id = ?";
        return query(sql, uid);
    }

    @Override
    public User getByName(String name) {
        String sql = "select * from user where name = ?";
        return query(sql, name);
    }

    @Override
    public void add(User user) {
        String sql = "insert into user(name, password) values(?, ?)";
        update(sql, user.getName(), user.getPassword());
    }

    @Override
    public User getByNameAndPwd(String name, String password) {
        String sql = "select * from user where name = ? and password = ?";
        return query(sql, name, password);
    }
}
