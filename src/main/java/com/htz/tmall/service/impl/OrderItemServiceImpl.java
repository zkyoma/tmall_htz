package com.htz.tmall.service.impl;

import com.htz.tmall.dao.common.CommonDao;
import com.htz.tmall.dao.OrderItemDao;
import com.htz.tmall.dao.ProductDao;
import com.htz.tmall.dao.impl.OrderItemDaoImpl;
import com.htz.tmall.dao.impl.ProductDaoImpl;
import com.htz.tmall.pojo.OrderItem;
import com.htz.tmall.pojo.Product;
import com.htz.tmall.pojo.ShoppingCart;
import com.htz.tmall.service.OrderItemService;

import java.util.List;

public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    private CommonDao commonDao = new CommonDao();

    @Override
    public void addCart(int pid, int num, int uid, ShoppingCart cart) {
        OrderItem orderItem = null;
        for(OrderItem oi : cart.getOrderItems()){
            if(oi.getPid() == pid && oi.getUid() == uid){
                orderItem = oi;
                //更新购物车
                oi.setNumber(num + oi.getNumber());
                //更新到数据库
                orderItemDao.updateNumber(orderItem.getId(), num + orderItem.getNumber());
                break;
            }
        }
        if(orderItem == null){
            orderItem = new OrderItem();
            orderItem.setUid(uid);
            orderItem.setPid(pid);
            orderItem.setNumber(num);
            //保存到数据库
            orderItemDao.add(orderItem);
            //加入购物车
            cart.getOrderItems().add(orderItem);
            Product product = productDao.getByPid(pid);
            commonDao.setFirstProductImage(product);
            orderItem.setProduct(product);
        }
        cart.setCartTotalItemNumber(cart.getCartTotalItemNumber() + num);
    }

    @Override
    public List<OrderItem> getByUid(int uid) {
        List<OrderItem> ois = orderItemDao.getByUid(uid);
        for(OrderItem oi : ois){
            Product product = productDao.getByPid(oi.getPid());
            commonDao.setFirstProductImage(product);
            commonDao.setReviewCount(product);
            commonDao.setSaleCount(product);
            oi.setProduct(product);
        }
        return ois;
    }

    @Override
    public void changeOrderItemNum(int oiid, int num) {
        orderItemDao.updateNumber(oiid, num);
    }

    @Override
    public void deleteById(int oiid) {
        orderItemDao.delete(oiid);
    }

    @Override
    public Float getOisByIds(String[] oiids, List<OrderItem> ois) {
        float total = 0;
        for(String oiid : oiids){
            OrderItem oi = orderItemDao.getById(Integer.parseInt(oiid));
            Product product = productDao.getByPid(oi.getPid());
            commonDao.setFirstProductImage(product);
            oi.setProduct(product);
            total += oi.getNumber() * product.getPromotePrice();
            ois.add(oi);
        }
        return total;
    }
}
