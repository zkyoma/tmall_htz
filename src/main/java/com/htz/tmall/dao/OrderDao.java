package com.htz.tmall.dao;

import com.htz.tmall.pojo.Order;

import java.util.List;

public interface OrderDao {
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    /**
     * 查询总记录数
     * @return
     */
    Long getTotalNumber();

    /**
     * 分页查询
     * @param start
     * @param pageSize
     * @return
     */
    List<Order> queryForPage(int start, int pageSize);

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    Order getByOid(int id);

    /**
     * 更新订单信息
     * @param order
     */
    void update(Order order);

    /**
     * 保存
     * @param order
     */
    void add(Order order);

    /**
     * 查询未删除的订单
     * @param uid
     * @return
     */
    List<Order> getByUidWithoutStatus(int uid, String status);
}
