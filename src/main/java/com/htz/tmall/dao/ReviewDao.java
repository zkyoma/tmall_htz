package com.htz.tmall.dao;

import com.htz.tmall.pojo.Review;

import java.util.List;

public interface ReviewDao {

    /**
     * 根据pid查询对应的review集合
     * @param pid
     * @return
     */
    List<Review> queryByPid(int pid);

    /**h
     * 查询pid对应的记录数
     * @param pid
     * @return
     */
    Long getCountByPid(int pid);

    /**
     * 保存评价
     * @param review
     */
    void add(Review review);
}
