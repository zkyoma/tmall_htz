package com.htz.tmall.service;

import com.htz.tmall.pojo.Property;
import com.htz.tmall.utils.Page;

public interface PropertyService {

    /**
     * 分页查询cid对应分类下的属性
     * @param cid
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<Property> getPage(int cid, int pageNo, int pageSize);

    /**
     * 添加属性
     * @param cid
     * @param name
     */
    void add(int cid, String name);

    /**
     * 删除属性
     * @param id
     */
    void delete(int id);

    /**
     * 获取属性property对象，同时填充category对象
     *
     * @param i
     * @param id
     * @return
     */
    Property get(int id);

    /**
     * 更新属性
     * @param id
     * @param name
     */
    void update(int id, String name);
}
