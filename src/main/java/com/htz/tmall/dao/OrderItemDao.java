package com.htz.tmall.dao;

import com.htz.tmall.pojo.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderItemDao {

    /**
     * 查询oid对应的记录集合
     * @param oid
     * @return
     */
    List<OrderItem> getByOid(int oid);

    /**
     * 查询pid对应订单项的总销量，不包括oid为-1的（没有生成订单，即仅仅加入购物车）
     * @param pid
     * @return
     */
    BigDecimal sumSaleCountQueryByPid(int pid);

    /**
     * 查询uid对应的orderItem集合
     * @param uid
     * @return
     */
    List<OrderItem> getByUid(int uid);

    /**
     * 保存
     * @param orderItem
     */
    void add(OrderItem orderItem);

    /**
     * 根据pid和uid查询orderItem
     * @param pid
     * @param uid
     * @return
     */
    OrderItem getByPidAndUid(int pid, int uid);

    /**
     * 更新orderItem的number
     * @param id
     * @param number
     */
    void updateNumber(int id, int number);

    /**
     * 删除
     * @param oiid
     */
    void delete(int oiid);

    /**
     * 根据id查询
     * @param oiid
     * @return
     */
    OrderItem getById(int oiid);

    /**
     * 更新oid
     * @param id
     * @param oid
     */
    void updateOid(int id, int oid);
}
