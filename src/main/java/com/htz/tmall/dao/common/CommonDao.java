package com.htz.tmall.dao.common;

import com.htz.tmall.dao.*;
import com.htz.tmall.dao.impl.*;
import com.htz.tmall.pojo.*;

import java.math.BigDecimal;
import java.util.List;

public class CommonDao {
    private PropertyDao propertyDao = new PropertyDaoImpl();
    private PropertyValueDao propertyValueDao = new PropertyValueDaoImpl();
    private ProductImageDao productImageDao = new ProductImageDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private ReviewDao reviewDao = new ReviewDaoImpl();

    /**
     * 初始化pid对应的属性信息
     * @param pid
     * @param cid
     */
    public void init(int pid, int cid){
        //查询该分类下的所有属性
        List<Property> properties = propertyDao.getByCid(cid);
        //遍历所有属性
        for(Property property : properties){
            //查询属性ptid和产品pid对应的属性值
            PropertyValue pv = propertyValueDao.getByPidAndPtid(pid, property.getId());
            if(pv == null){
                //若不存在，创建属性值对象，填充并保存
                pv = new PropertyValue();
                pv.setPid(pid);
                pv.setProperty(property);
                propertyValueDao.add(pv);
            }
        }
    }

    /**
     * 填充product的属性值propertyValue
     * @param product
     */
    public void fillValues(Product product){
        List<PropertyValue> pvs = propertyValueDao.getByPid(product.getId());
        for(PropertyValue pv : pvs){
            pv.setProperty(propertyDao.getById(pv.getPtid()));
        }
        product.setPropertyValues(pvs);
    }

    /**
     * 设置product集合的firstProductImage
     * @param products
     */
    public void setFirstProductImage(List<Product> products){
        if(products != null) {
            for (Product product : products) {
                setFirstProductImage(product);
            }
        }
    }

    /**
     * 设置product的firstProductImage
     * @param product
     */
    public void setFirstProductImage(Product product){
        List<ProductImage> productImages = productImageDao.queryByPidAndType(product.getId(), ProductImageDao.TYPE_SINGLE);
        if(productImages.size() > 0){
            product.setFirstProductImage(productImages.get(0));
        }
    }

    /**
     * 为category填充products
     * 为product设置firstProductImage
     * @param category
     */
    public void fillProducts(Category category){
        List<Product> products = productDao.getByCid(category.getId());
        setFirstProductImage(products);
        category.setProducts(products);
    }

    /**
     * 为product设置saleCount
     * @param product
     */
    public void setSaleCount(Product product){
        BigDecimal st = orderItemDao.sumSaleCountQueryByPid(product.getId());
        int saleCount = st == null ? 0 : Integer.valueOf(String.valueOf(st));
        product.setSaleCount(saleCount);
    }

    /**
     * 为product设置reviewCount
     */
    public void setReviewCount(Product product){
        int reviewCount = Math.toIntExact(reviewDao.getCountByPid(product.getId()));
        product.setReviewCount(reviewCount);
    }

    /**
     * 1. 为Order填充orderItems集合
     * 2. 为OrderItem填充product
     * 3. 为product填充firstProductImage
     * 4. 为Order设置totalMoney和totalNumber
     * @param order
     */
    public void fillOrderItems(Order order){
        //2. 填充订单项
        List<OrderItem> ois = orderItemDao.getByOid(order.getId());
        //3. 填充product
        float totalMoney = 0;
        int totalNumber = 0;
        for(OrderItem oi : ois){
            Product product = productDao.getByPid(oi.getPid());
            //设置firstProductImage
            setFirstProductImage(product);
            oi.setProduct(product);
            totalNumber += oi.getNumber();
            totalMoney += oi.getNumber() * product.getPromotePrice();
        }
        order.setTotalNumber(totalNumber);
        order.setTotalMoney(totalMoney);
        //设置orderItems集合
        order.setOrderItems(ois);
    }
}
