package com.htz.tmall.service;

import com.htz.tmall.pojo.Category;
import com.htz.tmall.utils.Page;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface CategoryService {

    /**
     * 获取分页对象
     * @param pageNo 当前页
     * @param pageSize 每页记录数
     * @return 分页对象 Page
     */
    Page<Category> getPage(int pageNo, int pageSize);

    /**
     * 上传图片
     * @param is 上传文件的输入流
     * @param params 参数集合
     * @param uploadPath
     */
    void add(InputStream is, Map<String, String> params, String uploadPath);

    /**
     * 删除操作
     *  1. 删除数据库对应的记录
     *  2. 删除分类对应的图片
     * @param id
     * @param uploadPath
     */
    void delete(int id, String uploadPath);

    /**
     * 获取分类
     * @param id
     * @return
     */
    Category get(int id);

    /**
     * 更新分类
     * @param is
     * @param params
     * @param uploadPath
     */
    void update(InputStream is, Map<String, String> params, String uploadPath);

    /**
     * 获取所有的category，并封装好product集合，便于前台显示
     * @return
     */
    List<Category> queryAll();

    /**
     * 查询category对象
     * 1. 填充对应的products
     * 2. 设置product的saleCount，reviewCount
     * @param cid
     * @return
     */
    Category getWithProducts(int cid);
}
