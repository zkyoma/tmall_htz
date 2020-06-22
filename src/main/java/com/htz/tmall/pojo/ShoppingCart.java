package com.htz.tmall.pojo;

import java.util.List;

public class ShoppingCart {

    /**
     * 购物车里的订单项集合
     */
    private List<OrderItem> orderItems;

    /**
     * 购物车的商品数量
     */
    private Integer cartTotalItemNumber;

    public ShoppingCart() {
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Integer getCartTotalItemNumber() {
        return cartTotalItemNumber;
    }

    public void setCartTotalItemNumber(Integer cartTotalItemNumber) {
        this.cartTotalItemNumber = cartTotalItemNumber;
    }
}
