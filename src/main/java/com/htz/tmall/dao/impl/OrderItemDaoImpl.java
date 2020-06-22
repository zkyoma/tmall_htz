package com.htz.tmall.dao.impl;

import com.htz.tmall.dao.OrderItemDao;
import com.htz.tmall.pojo.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public class OrderItemDaoImpl extends BaseDaoImpl<OrderItem> implements OrderItemDao {

    @Override
    public List<OrderItem> getByOid(int oid) {
        String sql = "select * from orderitem where oid = ?";
        return queryForList(sql, oid);
    }

    @Override
    public BigDecimal sumSaleCountQueryByPid(int pid) {
        String sql = "select sum(number) from orderitem where pid = ? and oid != -1";
        return getSingleVal(sql, pid);
    }

    @Override
    public List<OrderItem> getByUid(int uid) {
        String sql =  "select * from orderitem where uid = ? and oid = -1";
        return queryForList(sql, uid);
    }

    @Override
    public void add(OrderItem orderItem) {
        String sql = "insert into orderitem(pid, oid, uid, number) values(?, ?, ?, ?)";
        int id = insert(sql, orderItem.getPid(), -1, orderItem.getUid(), orderItem.getNumber());
        orderItem.setId(id);
    }

    @Override
    public OrderItem getByPidAndUid(int pid, int uid) {
        String sql = "select * from orderitem where pid = ? and uid = ? and oid = -1";
        return query(sql, pid, uid);
    }

    @Override
    public void updateNumber(int id, int number) {
        String sql = "update orderitem set number = ? where id = ?";
        update(sql, number, id);
    }

    @Override
    public void delete(int oiid) {
        String sql = "delete from orderitem where id = ?";
        update(sql, oiid);
    }

    @Override
    public OrderItem getById(int oiid) {
        String sql = "select * from orderitem where id = ?";
        return query(sql, oiid);
    }

    @Override
    public void updateOid(int id, int oid) {
        String sql = "update orderitem set oid = ? where id = ?";
        update(sql, oid, id);
    }
}
