package com.htz.tmall.service;

import com.htz.tmall.pojo.Product;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ProductImageService {

    /**
     * 获取product，并填充好category，productImage信息
     * @param pid
     * @return
     */
    Product get(int pid);

    /**
     * 添加product图片
     *  1. 添加数据库对应信息
     *  2. 上传图片
     *  3. 返回分类cid
     * @param is
     * @param pid
     * @param fileFolderPath
     * @param imageFolder_small
     * @param imageFolder_middle
     */
    Integer add(InputStream is, int pid, String type, String fileFolderPath, String imageFolder_small, String imageFolder_middle);

    /**
     * 删除图片，以及数据库记录
     * @param id
     */
    void delete(int id, String type, Map<String, List<String>> map);
}
