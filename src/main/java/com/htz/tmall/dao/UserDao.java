package com.htz.tmall.dao;

import com.htz.tmall.pojo.User;

import java.util.List;

public interface UserDao {

    /**
     * 获取总记录数
     * @return
     */
    Long getTotalNumber();

    /**
     * 查询该页对应的集合
     * @param start
     * @param pageSize
     * @return
     */
    List<User> queryForPage(int start, int pageSize);

    /**
     * 根据id查询user
     * @param uid
     * @return
     */
    User getByUid(int uid);

    /**
     * 根据用户名查询
     * @param name
     * @return
     */
    User getByName(String name);

    /**
     * 保存
     * @param user
     */
    void add(User user);

    /**
     * 根据name, pwd查询
     * @param name
     * @param password
     * @return
     */
    User getByNameAndPwd(String name, String password);
}
