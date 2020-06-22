package com.htz.tmall.service.impl;

import com.htz.tmall.dao.common.CommonDao;
import com.htz.tmall.dao.CategoryDao;
import com.htz.tmall.dao.impl.CategoryDaoImpl;
import com.htz.tmall.pojo.Category;
import com.htz.tmall.pojo.Product;
import com.htz.tmall.service.CategoryService;
import com.htz.tmall.utils.ImageUtil;
import com.htz.tmall.utils.Page;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private CommonDao commonDao = new CommonDao();

    @Override
    public Page<Category> getPage(int pageNo, int pageSize) {
        //1. 创建Page对象，设置参数
        //当前页
        Page<Category> page = new Page<>(pageNo);
        //每页显示记录数
        page.setPageSize(pageSize);
        //总记录数
        int totalNumber = Math.toIntExact(categoryDao.getTotalNumber());
        page.setTotalItemNumber(totalNumber);
        //开始位置，getPageNo()对pageNo进行了校验
        int start = (page.getPageNo() - 1) * pageSize;
        //category集合
        List<Category> categories = categoryDao.queryForPage(start, pageSize);
        //设置集合
        page.setList(categories);
        return page;
    }

    @Override
    public void add(InputStream is, Map<String, String> params, String uploadPath) {
        //1. 获取参数值
        //分类名称
        String name = params.get("name");
        //2. 创建分类对象
        Category c = new Category();
        c.setName(name);
        //3. 保存到数据库
        categoryDao.add(c);
        //4. 上传图片
        ImageUtil.uploadImage(is, uploadPath, c.getId(), null, null);
    }

    @Override
    public void delete(int id, String uploadPath) {
        //1. 删除记录
        categoryDao.delete(id);
        //2. 删除图片
        ImageUtil.deleteImage(uploadPath, id);
    }

    @Override
    public Category get(int id) {
        return categoryDao.getByCid(id);
    }

    @Override
    public void update(InputStream is, Map<String, String> params, String uploadPath) {
        //1. 获取参数
        String idStr = params.get("id");
        int id = 0;
        id = Integer.parseInt(idStr);
        String name = params.get("name");
        //2. 创建category对象
        Category c = new Category();
        c.setId(id);
        c.setName(name);
        //4. 更新数据库
        categoryDao.update(c);
        //5. 上传新的图片
        ImageUtil.uploadImage(is, uploadPath, c.getId(), null, null);
    }

    @Override
    public List<Category> queryAll() {
        //1. 查询所有category
        List<Category> cs = categoryDao.findAll();
        //2. 填充products 和 productsByRow
        int productNumberEachRow = 8;
        for(Category c : cs){
            //填充products, 设置firstProductImage
            commonDao.fillProducts(c);
            List<Product> products = c.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for(int i = 0; i < products.size(); i += productNumberEachRow){
                int size = i + productNumberEachRow;
                size = size > products.size() ? products.size() : size;
                List<Product> productsOfEachRow = products.subList(i, size);
                //填充productsByRow
                productsByRow.add(productsOfEachRow);
            }
            c.setProductsByRow(productsByRow);
        }
        return cs;
    }

    @Override
    public Category getWithProducts(int cid) {
        //1. 查询category
        Category category = categoryDao.getByCid(cid);
        if(category == null){
            return null;
        }
        //2. 设置对应的products集合
        commonDao.fillProducts(category);
        //3. 设置products的saleCount，reviewCount
        for(Product product : category.getProducts()){
            commonDao.setSaleCount(product);
            commonDao.setReviewCount(product);
        }
        return category;
    }
}
