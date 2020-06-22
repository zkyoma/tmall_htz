package com.htz.tmall.dao.impl;

import com.htz.tmall.dao.ReviewDao;
import com.htz.tmall.pojo.Review;
import com.htz.tmall.utils.DateUtil;

import java.util.List;

public class ReviewDaoImpl extends BaseDaoImpl<Review> implements ReviewDao {
    @Override
    public List<Review> queryByPid(int pid) {
        String sql = "select * from review where pid = ?";
        return queryForList(sql, pid);
    }

    @Override
    public Long getCountByPid(int pid) {
        String sql = "select count(id) from review where pid = ?";
        return getSingleVal(sql, pid);
    }

    @Override
    public void add(Review review) {
        String sql = "insert into review values(null, ?, ?, ?, ?)";
        update(sql, review.getContent(), review.getUid(), review.getPid(), DateUtil.d2t(review.getCreateDate()));
    }
}
