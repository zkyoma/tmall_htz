package com.htz.tmall.dao;

import com.htz.tmall.pojo.Category;

import java.util.List;

public interface CategoryDao {

    /**
     * 根据id获取Category对象
     * @param id
     * @return
     */
    Category getByCid(int cid);

    /**
     * 更新category
     * @param category
     */
    void update(Category category);

    /**
     * 分页查询
     * @param start
     * @param count
     * @return
     */
    List<Category> queryForPage(int start, int count);

    /**
     * 保存
     * @param category
     */
    void add(Category category);

    /**
     * 获取总记录数
     * @return
     */
    Long getTotalNumber();

    /**
     * 删除分类
     * @param id
     */
    void delete(int id);

    /**
     * 查询所有category
     * @return
     */
    List<Category> findAll();
}
