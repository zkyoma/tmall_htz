package com.htz.tmall.service.impl;

import com.htz.tmall.dao.common.CommonDao;
import com.htz.tmall.dao.*;
import com.htz.tmall.dao.impl.*;
import com.htz.tmall.pojo.*;
import com.htz.tmall.service.OrderService;
import com.htz.tmall.utils.Page;

import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private CommonDao commonDao = new CommonDao();
    private ReviewDao reviewDao = new ReviewDaoImpl();

    @Override
    public Page<Order> getPage(int pageNo, int pageSize) {
        //1. 创建Page对象
        Page<Order> page = new Page<>(pageNo);
        page.setPageSize(pageSize);
        //2. 查询总记录数
        int totalItemNumber = Math.toIntExact(orderDao.getTotalNumber());
        page.setTotalItemNumber(totalItemNumber);
        //3. 计算开始位置
        int start = (page.getPageNo() - 1) * pageSize;
        //4. 分页查询
        List<Order> orders = orderDao.queryForPage(start, pageSize);
        page.setList(orders);
        //5. 填充订单项
        for(Order order : orders){
            commonDao.fillOrderItems(order);
            User user = userDao.getByUid(order.getUid());
            order.setUser(user);
        }
        return page;
    }

    @Override
    public Order update(int id, String status) {
        Order order = orderDao.getByOid(id);
        //设置时间
        switch (status){
            case OrderDao.waitReview :
                order.setConfirmDate(new Date());
                break;
            case OrderDao.waitDelivery :
                order.setPayDate(new Date());
                break;
        }
        //设置订单状态
        order.setStatus(status);
        //更新订单信息
        orderDao.update(order);
        return order;
    }

    @Override
    public Float addOrderAndUpdateOderItem(Order order, List<OrderItem> ois, boolean buNow) {
        //1. 保存订单
        orderDao.add(order);
        //2. 更新订单项
        float total = 0;
        for(OrderItem oi : ois){
            if(buNow) {
                orderItemDao.add(oi);
            }
            orderItemDao.updateOid(oi.getId(), order.getId());
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
        }
        return total;
    }

    @Override
    public List<Order> getOrderByUidWithoutDelete(int uid) {
        List<Order> os = orderDao.getByUidWithoutStatus(uid, OrderDao.delete);
        for(Order o : os){
            commonDao.fillOrderItems(o);
        }
        return os;
    }

    @Override
    public Order getByOidWithOis(int oid) {
        //1. 查询order
        Order order = orderDao.getByOid(oid);
       //2. 填充orderItems
        commonDao.fillOrderItems(order);
        return order;
    }

    @Override
    public Order getByOidWithOisAndReviews(int oid) {
        Order order = getByOidWithOis(oid);
        List<OrderItem> ois = order.getOrderItems();
        for(OrderItem oi : ois){
            Product product = oi.getProduct();
            commonDao.setSaleCount(product);
            commonDao.setReviewCount(product);
            List<Review> reviews = reviewDao.queryByPid(product.getId());
            product.setReviews(reviews);
        }
        return order;
    }

    @Override
    public void review(int oid, int pid, int uid, String content) {
        //1. 修改订单status为finish
        update(oid, OrderDao.finish);
        //2. 保存review
        Review review = new Review();
        //设置评价内容
        review.setContent(content);
        //设置uid
        review.setUid(uid);
        Product product = productDao.getByPid(pid);
        //设置pid
        review.setPid(product.getId());
        //设置日期
        review.setCreateDate(new Date());
        //保存
        reviewDao.add(review);
    }
}
