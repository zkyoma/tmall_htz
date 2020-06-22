package com.htz.tmall.dao;

import com.htz.tmall.pojo.ProductImage;

import java.util.List;

public interface ProductImageDao {
    String TYPE_SINGLE = "type_single";
    String TYPE_DETAIL = "type_detail";


    /**
     * 根据pid和type查询图片集合
     * @param pid
     * @return
     */
    List<ProductImage> queryByPidAndType(int pid, String type);

    /**
     * 删除pid对应的图片
     * @param pid
     */
    void deleteByPid(int pid);

    /**
     * 获取pid对应的图片集合
     * @return
     */
    List<ProductImage> queryByPid(int pid);

    /**
     * 保存图片记录
     * @param productImage
     */
    void add(ProductImage productImage);

    /**
     * 删除图片记录
     * @param id
     */
    void deleteById(int id);
}
