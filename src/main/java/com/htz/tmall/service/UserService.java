package com.htz.tmall.service;

import com.htz.tmall.pojo.User;
import com.htz.tmall.utils.Page;

public interface UserService {

    /**
     * 获取user对应的page对象
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<User> getPage(int pageNo, int pageSize);

    /**
     * 判断此用户是否存在
     * @param name
     * @return
     */
    boolean isExistUser(String name);

    /**
     * 保存用户
     * @param user
     */
    void add(User user);

    /**
     * 根据name和pwd查询用户
     * @param name
     * @param password
     * @return
     */
    User getByNameAndPwd(String name, String password);
}
