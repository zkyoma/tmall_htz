package com.htz.tmall.service.impl;

import com.htz.tmall.dao.CategoryDao;
import com.htz.tmall.dao.ProductDao;
import com.htz.tmall.dao.ProductImageDao;
import com.htz.tmall.dao.impl.CategoryDaoImpl;
import com.htz.tmall.dao.impl.ProductDaoImpl;
import com.htz.tmall.dao.impl.ProductImageDaoImpl;
import com.htz.tmall.pojo.Category;
import com.htz.tmall.pojo.Product;
import com.htz.tmall.pojo.ProductImage;
import com.htz.tmall.service.ProductImageService;
import com.htz.tmall.utils.ImageUtil;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ProductImageServiceImpl implements ProductImageService {
    private ProductImageDao productImageDao = new ProductImageDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();

    @Override
    public Product get(int pid) {
        Product product = productDao.getByPid(pid);
        if(product == null){
            return null;
        }
        int cid = product.getCid();
        Category category = categoryDao.getByCid(cid);

        List<ProductImage> singleImages = productImageDao.queryByPidAndType(pid, ProductImageDao.TYPE_SINGLE);
        List<ProductImage> detail_Images = productImageDao.queryByPidAndType(pid, ProductImageDao.TYPE_DETAIL);

        product.setCategory(category);
        product.setProductDetailImages(detail_Images);
        product.setProductSingleImages(singleImages);

        return product;
    }

    @Override
    public Integer add(InputStream is, int pid, String type, String fileFolderPath, String imageFolder_small, String imageFolder_middle) {
        //1. 创建productImage对象
        ProductImage productImage = new ProductImage();
        productImage.setType(type);
        Product product = productDao.getByPid(pid);
        productImage.setProduct(product);
        //2. 保存
        productImageDao.add(productImage);
        //3. 上传图片
        ImageUtil.uploadImage(is, fileFolderPath, productImage.getId(), imageFolder_small, imageFolder_middle);
        return product.getCid();
    }

    @Override
    public void delete(int id, String type, Map<String, List<String>> map) {
        //1. 删除图片
        List<String> filePaths = map.get(type);
        for(String filePath : filePaths){
            ImageUtil.deleteImage(filePath, id);
        }
        //2. 删除数据库记录
        productImageDao.deleteById(id);
    }
}
