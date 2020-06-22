package com.htz.tmall.service.impl;

import com.htz.tmall.dao.CategoryDao;
import com.htz.tmall.dao.PropertyDao;
import com.htz.tmall.dao.impl.CategoryDaoImpl;
import com.htz.tmall.dao.impl.PropertyDaoImpl;
import com.htz.tmall.pojo.Category;
import com.htz.tmall.pojo.Property;
import com.htz.tmall.service.PropertyService;
import com.htz.tmall.utils.Page;

import java.util.List;

public class PropertyServiceImpl implements PropertyService {
    private PropertyDao propertyDao = new PropertyDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public Page<Property> getPage(int cid, int pageNo, int pageSize) {
        //1. 创建Page对象
        Page<Property> page = new Page<>(pageNo);
        page.setPageSize(pageSize);
        //2. 查询总记录数
        int totalNumber = Math.toIntExact(propertyDao.getTotalNumberByCid(cid));
        page.setTotalItemNumber(totalNumber);
        //3. 计算开始查询位置
        int start = (page.getPageNo() - 1) * pageSize;
        //4. 分页查询属性集合
        List<Property> properties = propertyDao.queryForPageByCid(cid, start, pageSize);
        page.setList(properties);
        return page;
    }

    @Override
    public void add(int cid, String name) {
        Property property = new Property();
        property.setName(name);
        Category c = categoryDao.getByCid(cid);
        property.setCategory(c);
        propertyDao.add(property);
    }

    @Override
    public void delete(int id) {
        propertyDao.delete(id);
    }

    @Override
    public Property get(int id) {
        //1. 获取property对象
        Property property = propertyDao.getById(id);
        //2. 获取category对象
        int cid = property.getCid();
        Category category = categoryDao.getByCid(cid);
        property.setCategory(category);
        return property;
    }

    @Override
    public void update(int id, String name) {
        Property property = new Property();
        property.setName(name);
        property.setId(id);
        propertyDao.update(property);
    }
}
