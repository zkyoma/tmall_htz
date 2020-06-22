package com.htz.tmall.service;

import com.htz.tmall.pojo.OrderItem;
import com.htz.tmall.pojo.ShoppingCart;

import java.util.List;

public interface OrderItemService {

    /**
     * 增加购物车业务
     * 1. 根据uid，pid查询orderItem
     * 2. 判断orderItem是否为空
     * 3. 为空，创建orderItem对象，保存到数据库
     * 4. 不为空，更新product数量到数据库
     * @param pid
     * @param num
     * @param uid
     */
    void addCart(int pid, int num, int uid, ShoppingCart cart);

    /**
     * 查询uid对应的orderItem集合
     * @param uid
     * @return
     */
    List<OrderItem> getByUid(int uid);

    /**
     * 改变orderItem的数量
     * @param pid
     * @param num
     */
    void changeOrderItemNum(int oiid, int num);

    /**
     * 根据id删除
     * @param oiid
     */
    void deleteById(int oiid);

    /**
     * 查询对应的订单项集合，计算并返回总金额
     * @param oiids
     * @param ois
     * @return
     */
    Float getOisByIds(String[] oiids, List<OrderItem> ois);
}
