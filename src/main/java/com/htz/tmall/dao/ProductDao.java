package com.htz.tmall.dao;

import com.htz.tmall.pojo.Product;

import java.util.List;

public interface ProductDao {

    /**
     * 获取cid对应的product的总记录数
     * @param cid
     * @return
     */
    Long getTotalNumber(int cid);

    /**
     * 分页查询cid对应的product集合
     * @param start
     * @param pageSize
     * @param cid
     * @return
     */
    List<Product> queryForPage(int start, int pageSize, int cid);

    /**
     * 保存product
     * @param product
     */
    void add(Product product);

    /**
     * 删除product
     * @param id
     */
    void delete(int id);

    /**
     * 根据id查询product
     * @param pid
     * @return
     */
    Product getByPid(int id);

    /**
     * 更新product
     * @param product
     */
    void update(Product product);

    /**
     * 查询cid对应的product集合
     * @param id
     * @return
     */
    List<Product> getByCid(int cid);

    /**
     * 根据名称模糊查询
     * @param keyword
     * @return
     */
    List<Product> queryByKeyword(String keyword);
}
