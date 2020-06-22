package com.htz.tmall.dao.impl;

import com.htz.tmall.dao.ProductImageDao;
import com.htz.tmall.pojo.ProductImage;

import java.util.List;

public class ProductImageDaoImpl extends BaseDaoImpl<ProductImage> implements ProductImageDao {

    @Override
    public List<ProductImage> queryByPidAndType(int pid, String type) {
        String sql = "select * from productimage where pid = ? and type = ?";
        return queryForList(sql, pid, type);
    }

    @Override
    public void deleteByPid(int pid) {
        String sql = "deleteByPid from productimage where pid = ?";
        update(sql, pid);
    }

    @Override
    public List<ProductImage> queryByPid(int pid) {
        String sql = "select * from productimage where pid = ?";
        return queryForList(sql, pid);
    }

    @Override
    public void add(ProductImage productImage) {
        String sql = "insert into productimage(pid, type) values(?, ?)";
        int id = insert(sql, productImage.getProduct().getId(), productImage.getType());
        productImage.setId(id);
    }

    @Override
    public void deleteById(int id) {
        String sql = "delete from productimage where id = ?";
        update(sql, id);
    }
}
