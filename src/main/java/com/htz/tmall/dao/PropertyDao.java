package com.htz.tmall.dao;

import com.htz.tmall.pojo.Property;

import java.util.List;

public interface PropertyDao {

    /**
     * 分页查询cid对应分类下属性
     * @param cid
     * @param start
     * @param count
     * @return
     */
    List<Property> queryForPageByCid(int cid, int start, int count);

    /**
     * 查询cid对应分类下的属性的记录数
     * @param cid
     * @return
     */
    Long getTotalNumberByCid(int cid);

    /**
     * 添加属性
     * @param property
     */
    void add(Property property);

    /**
     * 删除属性
     * @param id
     */
    void delete(int id);

    /**
     * 根据id查询property
     * @param id
     * @return
     */
    Property getById(int id);

    /**
     * 更新property
     * @param property
     */
    void update(Property property);

    /**
     * 查询cid对应的所有属性
     * @param cid
     * @return
     */
    List<Property> getByCid(int cid);
}
