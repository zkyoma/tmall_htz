package com.htz.tmall.dao.impl;

import com.htz.tmall.dao.OrderDao;
import com.htz.tmall.pojo.Order;
import com.htz.tmall.utils.DateUtil;

import java.util.List;

public class OrderDaoImpl extends BaseDaoImpl<Order> implements OrderDao {

    @Override
    public Long getTotalNumber() {
        String sql = "select count(id) from order_";
        return getSingleVal(sql);
    }

    @Override
    public List<Order> queryForPage(int start, int pageSize) {
        String sql = "select * from order_ limit ?, ?";
        return queryForList(sql, start, pageSize);
    }

    @Override
    public Order getByOid(int id) {
        String sql = "select * from order_ where id = ?";
        return query(sql, id);
    }

    @Override
    public void update(Order order) {
        String sql = "update order_ set address = ?, post = ?, receiver = ?, mobile = ?, " +
                "userMessage = ? ,createDate = ?, payDate = ?, deliveryDate =?, confirmDate = ?," +
                " orderCode = ?, uid = ?, status = ? where id = ?";
        update(sql, order.getAddress(), order.getPost(), order.getReceiver(), order.getMobile(),
                order.getUserMessage(), DateUtil.d2t(order.getCreateDate()), DateUtil.d2t(order.getPayDate()), DateUtil.d2t(order.getDeliveryDate()),
                DateUtil.d2t(order.getConfirmDate()), order.getOrderCode(), order.getUid(), order.getStatus(), order.getId());
    }

    @Override
    public void add(Order order) {
        String sql = "insert into order_ values(null,?,?,?,?,?,?,?,?,?,?,?,?)";
        int id = insert(sql, order.getOrderCode(), order.getAddress(), order.getPost(), order.getReceiver(), order.getMobile(),
                order.getUserMessage(), DateUtil.d2t(order.getCreateDate()), DateUtil.d2t(order.getPayDate()), DateUtil.d2t(order.getDeliveryDate()),
                DateUtil.d2t(order.getConfirmDate()), order.getUser().getId(), order.getStatus());
        order.setId(id);
    }

    @Override
    public List<Order> getByUidWithoutStatus(int uid, String status) {
        String sql = "select * from order_ where uid = ? and status != ?";
        return queryForList(sql, uid, status);
    }
}
