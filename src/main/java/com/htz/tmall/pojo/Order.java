package com.htz.tmall.pojo;

import com.htz.tmall.dao.OrderDao;

import java.util.Date;
import java.util.List;

public class Order {
    private Integer id;
    private Integer uid;
    private String orderCode;  //订单号
    private String address;  //收货地址
    private String post;  //邮编
    private String receiver;  //收货人信息
    private String mobile;  //手机号码
    private String userMessage; //用户备注信息
    private Date createDate;  //订单创建日期
    private Date payDate;  //支付日期
    private Date deliveryDate;  //发货日期
    private Date confirmDate;  //确认收货日期
    private String status;  //订单状态(待发货，完成，删除，等评价)
    private User user;  //订单与用户多对一
    private List<OrderItem> orderItems;  //订单与订单项一对多
    private Float totalMoney;  //订单总金额
    private Integer totalNumber;  //订单总数量

    public Order() {
    }

    public String getStatusDesc() {
        String desc = "未知";
        switch (status) {
            case OrderDao.waitPay:
                desc = "待付款";
                break;
            case OrderDao.waitDelivery:
                desc = "待发货";
                break;
            case OrderDao.waitConfirm:
                desc = "待收货";
                break;
            case OrderDao.waitReview:
                desc = "等评价";
                break;
            case OrderDao.finish:
                desc = "完成";
                break;
            case OrderDao.delete:
                desc = "刪除";
                break;
            default:
                desc = "未知";
        }
        return desc;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }
}
