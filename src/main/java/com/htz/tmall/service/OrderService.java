package com.htz.tmall.service;

import com.htz.tmall.pojo.Order;
import com.htz.tmall.pojo.OrderItem;
import com.htz.tmall.utils.Page;

import java.util.List;

public interface OrderService {

    /**
     * 获取order对应的page对象，order填充orderItem
     * @param pageNo
     * @param pageSize
     * @return
     */
    Page<Order> getPage(int pageNo, int pageSize);

    /**
     * 更新订单，并返回
     * @param id
     */
    Order update(int id, String status);

    /**
     * 保存订单，并更新订单项，计算总金额并返回
     * @param order
     * @param ois
     */
    Float addOrderAndUpdateOderItem(Order order, List<OrderItem> ois, boolean buyNow);

    /**
     * 查询user未删除的订单，并填充订单项集合
     * @param id
     * @return
     */
    List<Order> getOrderByUidWithoutDelete(int id);

    /**
     * 根据oid查询订单，并填充订单项集合，填充product以及firstProductImage
     * @param oid
     * @return
     */
    Order getByOidWithOis(int oid);

    /**
     * 根据oid查询订单
     * 填充订单项
     * 为订单项填充产品
     * 为产品填充firstProductImage，saleCount，reviewCount，reviews集合，
     * @param oid
     */
    Order getByOidWithOisAndReviews(int oid);

    /**
     * 评价
     * 修改订单status为finish
     * 保存review
     * @param oid
     * @param pid
     */
    void review(int oid, int pid, int uid, String content);
}
