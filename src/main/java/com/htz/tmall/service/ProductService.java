package com.htz.tmall.service;

import com.htz.tmall.pojo.Product;
import com.htz.tmall.utils.Page;

import java.util.List;
import java.util.Map;

public interface ProductService {

    /**
     * 获取cid对应分类下的product集合
     * @param pageNo
     * @param pageSize
     * @param cid
     * @return
     */
    Page<Product> getPage(int pageNo, int pageSize, int cid);

    /**
     * 保存product
     * @param product
     */
    void add(Product product);

    /**
     * 删除product和对应的图片
     * @param id
     */
    void deleteProductAndImagesAndPvs(int id, Map<String, List<String>> imgFilePathMap);

    /**
     * 查询product
     *  flag为true: 填充category，property，propertyValue集合
     *  flag为false: 填充category
     * @param id
     * @param cid
     * @return
     */
    Product get(int id, boolean flag);

    /**
     * 更新propertyValue
     * @param pvid
     * @param value
     */
    void updatePropertyValue(int pvid, String value);

    /**
     * 更新product
     * @param product
     */
    void updateProduct(Product product);

    /**
     * 获取product，填充productImage
     * @param pid
     * @return
     */
    Product getProAndImg(int pid);

    /**
     * 获取product
     *  1. 设置category，images，reviews
     *  2. 为review设置user
     * @param pid
     * @return
     */
    Product getAndFillAll(int pid);

    /**
     * 根据关键字进行模糊查询
     * @param keyword
     * @return
     */
    List<Product> search(String keyword);
}
