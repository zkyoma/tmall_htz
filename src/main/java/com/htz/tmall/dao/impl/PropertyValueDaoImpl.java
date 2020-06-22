package com.htz.tmall.dao.impl;

import com.htz.tmall.dao.PropertyValueDao;
import com.htz.tmall.pojo.PropertyValue;

import java.util.List;

public class PropertyValueDaoImpl extends BaseDaoImpl<PropertyValue> implements PropertyValueDao {

    @Override
    public List<PropertyValue> getByPid(int pid) {
        String sql = "select * from propertyValue where pid = ?";
        return queryForList(sql, pid);
    }

    @Override
    public void add(PropertyValue pv) {
        String sql = "insert into propertyValue(pid, ptid, value) values(?, ?, ?)";
        Integer id = insert(sql, pv.getPid(), pv.getProperty().getId(), pv.getValue());
        pv.setId(id);
    }

    @Override
    public PropertyValue getByPidAndPtid(int pid, int ptid) {
        String sql = "select * from propertyValue where pid = ? and ptid = ?";
        return query(sql, pid, ptid);
    }

    @Override
    public void update(PropertyValue pv) {
        String sql = "update propertyValue set value = ? where id = ?";
        update(sql, pv.getValue(), pv.getId());
    }

    @Override
    public void deleteByPid(int pid) {
        String sql = "deleteByPid from propertyValue where pid = ?";
        update(sql, pid);
    }
}
