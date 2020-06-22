package com.htz.tmall.dao.impl;

import com.htz.tmall.dao.PropertyDao;
import com.htz.tmall.pojo.Property;

import java.util.List;

public class PropertyDaoImpl extends BaseDaoImpl<Property> implements PropertyDao {

    @Override
    public List<Property> queryForPageByCid(int cid, int start, int count) {
        String sql = "select * from property where cid = ? limit ?, ?";
        return queryForList(sql, cid, start, count);
    }

    @Override
    public Long getTotalNumberByCid(int cid) {
        String sql = "select count(id) from property where cid = ?";
        return getSingleVal(sql, cid);
    }

    @Override
    public void add(Property property) {
        String sql = "insert into property(cid, name) values(?, ?)";
        insert(sql, property.getCategory().getId(), property.getName());
    }

    @Override
    public void delete(int id) {
        String sql = "deleteByPid from property where id = ?";
        update(sql, id);
    }

    @Override
    public Property getById(int id) {
        String sql = "select * from property where id = ?";
        return query(sql, id);
    }

    @Override
    public void update(Property property) {
        String sql = "update property set name = ? where id = ?";
        update(sql, property.getName(), property.getId());
    }

    @Override
    public List<Property> getByCid(int cid) {
        String sql = "select * from property where cid = ?";
        return queryForList(sql, cid);
    }
}
