package com.htz.tmall.service.impl;

import com.htz.tmall.dao.common.CommonDao;
import com.htz.tmall.dao.*;
import com.htz.tmall.dao.impl.*;
import com.htz.tmall.pojo.*;
import com.htz.tmall.service.ProductService;
import com.htz.tmall.utils.ImageUtil;
import com.htz.tmall.utils.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao = new ProductDaoImpl();
    private ProductImageDao productImageDao = new ProductImageDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private PropertyValueDao propertyValueDao = new PropertyValueDaoImpl();
    private CommonDao commonDao = new CommonDao();
    private ReviewDao reviewDao = new ReviewDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();

    @Override
    public Page<Product> getPage(int pageNo, int pageSize, int cid) {
        //1. 创建page对象
        Page<Product> page = new Page<>(pageNo);
        page.setPageSize(pageSize);
        //2. 获取总记录数
        int totalNumber = Math.toIntExact(productDao.getTotalNumber(cid));
        page.setTotalItemNumber(totalNumber);
        //3. 获取product集合
        int start = (page.getPageNo() - 1) * pageSize;
        List<Product> products = productDao.queryForPage(start, pageSize, cid);
        //4. 设置product展示图片
        commonDao.setFirstProductImage(products);
        page.setList(products);
        return page;
    }

    @Override
    public void add(Product product) {
        productDao.add(product);
    }

    @Override
    public void deleteProductAndImagesAndPvs(int id, Map<String, List<String>> imgFilePathMap) {
        //1. 获取图片记录集合
        List<ProductImage> images = productImageDao.queryByPid(id);
        //2. 删除图片文件
        for(ProductImage image : images){
            int imageId = image.getId();
            List<String> filePathList = imgFilePathMap.get(image.getType());
            for(String filePath : filePathList){
                ImageUtil.deleteImage(filePath, imageId);
            }
        }
        //3. 删除数据库图片记录
        productImageDao.deleteByPid(id);
        //4. 删除propertyValues
        propertyValueDao.deleteByPid(id);
        //5. 删除product
        productDao.delete(id);

    }

    @Override
    public Product get(int id, boolean flag) {
        //1. 获取product对象
        Product product = productDao.getByPid(id);
        if(product == null){
            return null;
        }
        int cid = product.getCid();
        //2. 获取category对象
        Category category = categoryDao.getByCid(cid);
        product.setCategory(category);
        if(flag) {
            //3. 初始化product的属性
            commonDao.init(id, cid);
            //4. 获取propertyValues集合
            commonDao.fillValues(product);
        }
        //5. 返回product
        return product;
    }

    @Override
    public Product getProAndImg(int id){
        //1. 获取product对象
        Product product = productDao.getByPid(id);
        //2. 填充productImage
        commonDao.setFirstProductImage(product);
        return product;
    }

    @Override
    public Product getAndFillAll(int pid) {
        //1. 根据pid查询product
        Product product = productDao.getByPid(pid);
        if(product == null){
            return null;
        }
        //2. 查询分类
        Category category = categoryDao.getByCid(product.getCid());
        product.setCategory(category);
        //3. 查询productImage
        //single
        List<ProductImage> single_images = productImageDao.queryByPidAndType(pid, ProductImageDao.TYPE_SINGLE);
        product.setProductDetailImages(single_images);
        //firstProductImage
        if(single_images.size() > 0){
            product.setFirstProductImage(single_images.get(0));
        }
        //detail
        List<ProductImage> detail_images = productImageDao.queryByPidAndType(pid, ProductImageDao.TYPE_DETAIL);
        product.setProductDetailImages(detail_images);
        //4. 根据pid查询对应的review集合
        List<Review> reviews = reviewDao.queryByPid(pid);
        product.setReviews(reviews);
        //5. 为review设置user
        for(Review review : reviews){
            User user = userDao.getByUid(review.getUid());
            review.setUser(user);
        }
        //6. 设置评价数量
        int reviewCount = reviews.size();
        product.setReviewCount(reviewCount);
        //7. 设置product的saleCount
        BigDecimal st = orderItemDao.sumSaleCountQueryByPid(pid);
        int saleCount = st == null ? 0 : Integer.valueOf(String.valueOf(st));
        product.setSaleCount(saleCount);
        //8. 设置product的属性值propertyValues
        commonDao.fillValues(product);
        return product;
    }

    @Override
    public List<Product> search(String keyword) {
        //1. 获取products集合
        List<Product> products = productDao.queryByKeyword(keyword);
        //2. 设置firstProductImage
        commonDao.setFirstProductImage(products);
        //3. 设置saleCount和reviewCount
        for(Product product : products){
            commonDao.setSaleCount(product);
            commonDao.setReviewCount(product);
        }
        return products;
    }

    @Override
    public void updatePropertyValue(int pvid, String value) {
        PropertyValue pv = new PropertyValue();
        pv.setId(pvid);
        pv.setValue(value);
        propertyValueDao.update(pv);
    }

    @Override
    public void updateProduct(Product product) {
        productDao.update(product);
    }
}
