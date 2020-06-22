package com.htz.tmall.service.impl;

import com.htz.tmall.dao.UserDao;
import com.htz.tmall.dao.impl.UserDaoImpl;
import com.htz.tmall.pojo.User;
import com.htz.tmall.service.UserService;
import com.htz.tmall.utils.Page;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public Page<User> getPage(int pageNo, int pageSize) {
        //1. 创建page对象
        Page<User> page = new Page<>(pageNo);
        page.setPageSize(pageSize);
        //2. 获取总记录数
        int totalNumber = Math.toIntExact(userDao.getTotalNumber());
        page.setTotalItemNumber(totalNumber);
        //3. 计算开始查询位置
        int start = (page.getPageNo() - 1) * pageSize;
        //4. 查询该页的记录集合
        List<User> list = userDao.queryForPage(start, pageSize);
        page.setList(list);
        return page;
    }

    @Override
    public boolean isExistUser(String name) {
        User user = userDao.getByName(name);
        return user != null;
    }

    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Override
    public User getByNameAndPwd(String name, String password) {
        return userDao.getByNameAndPwd(name, password);
    }
}
