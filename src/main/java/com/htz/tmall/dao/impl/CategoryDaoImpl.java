package com.htz.tmall.dao.impl;

import com.htz.tmall.dao.CategoryDao;
import com.htz.tmall.pojo.Category;

import java.util.List;

public class CategoryDaoImpl extends BaseDaoImpl<Category> implements CategoryDao {
    @Override
    public Category getByCid(int cid) {
        String sql = "select * from category where id = ?";
        return query(sql, cid);
    }

    @Override
    public void update(Category category) {
        String sql = "update category set name = ? where id = ?";
        update(sql, category.getName(), category.getId());
    }

    @Override
    public List<Category> queryForPage(int start, int count) {
        String sql = "select * from category limit ?, ?";
        return queryForList(sql, start, count);
    }

    @Override
    public void add(Category category) {
        String sql = "insert into category(name) values(?)";
        int id = insert(sql, category.getName());
        category.setId(id);
    }

    @Override
    public Long getTotalNumber() {
        String sql = "select count(id) from category";
        return getSingleVal(sql);
    }

    public void delete(int id){
        String sql = "deleteByPid from category where id = ?";
        update(sql, id);
    }

    @Override
    public List<Category> findAll() {
        String sql = "select * from category";
        return queryForList(sql);
    }
}
