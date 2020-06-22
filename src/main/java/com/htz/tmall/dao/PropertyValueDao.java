package com.htz.tmall.dao;

import com.htz.tmall.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueDao {

    /**
     * 根据pid查询产品对应的属性集合
     * @param pid
     * @return
     */
    List<PropertyValue> getByPid(int pid);

    /**
     * 保存属性值
     * @param pv
     */
    void add(PropertyValue pv);

    /**
     * 根据产品pid和属性ptid查询属性值对象
     * @param id
     * @param id1
     * @return
     */
    PropertyValue getByPidAndPtid(int pid, int ptid);

    /**
     * 更新
     * @param pv
     */
    void update(PropertyValue pv);

    /**
     * 删除pid对应的记录
     * @param pid
     */
    void deleteByPid(int pid);
}
