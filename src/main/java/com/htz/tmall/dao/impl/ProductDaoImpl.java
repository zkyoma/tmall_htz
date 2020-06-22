package com.htz.tmall.dao.impl;

import com.htz.tmall.dao.ProductDao;
import com.htz.tmall.pojo.Product;
import com.htz.tmall.utils.DateUtil;

import java.util.List;

public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao {
    @Override
    public Long getTotalNumber(int cid) {
        String sql = "select count(id) from product where cid = ?";
        return getSingleVal(sql, cid);
    }

    @Override
    public List<Product> queryForPage(int start, int pageSize, int cid) {
        String sql = "select * from product where cid = ? limit ?, ?";
        return queryForList(sql, cid, start, pageSize);
    }

    @Override
    public void add(Product product) {
        String sql = "insert into product values(null,?,?,?,?,?,?,?)";
        update(sql, product.getName(), product.getSubTitle(), product.getOriginalPrice(), product.getPromotePrice(),
                product.getStock(), product.getCategory().getId(), DateUtil.d2t(product.getCreateDate()));
    }

    @Override
    public void delete(int id) {
        String sql = "deleteByPid from product where id = ?";
        update(sql, id);
    }

    @Override
    public Product getByPid(int id) {
        String sql = "select * from product where id = ?";
        return query(sql, id);
    }

    @Override
    public void update(Product product) {
        String sql = "update product set name = ?, subTitle = ?, originalPrice = ?, promotePrice = ?, " +
                "stock = ?, cid = ?, createDate = ? where id = ?";
        update(sql, product.getName(), product.getSubTitle(), product.getOriginalPrice(), product.getPromotePrice(),
                product.getStock(), product.getCid(), DateUtil.d2t(product.getCreateDate()), product.getId());
    }

    @Override
    public List<Product> getByCid(int cid) {
        String sql = "select * from product where cid = ?";
        return queryForList(sql, cid);
    }

    @Override
    public List<Product> queryByKeyword(String keyword) {
        String sql = "select * from product where name like ?";
        return queryForList(sql, "%" + keyword + "%");
    }
}
