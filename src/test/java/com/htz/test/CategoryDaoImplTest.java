package com.htz.test;

import com.htz.tmall.dao.CategoryDao;
import com.htz.tmall.dao.impl.CategoryDaoImpl;
import com.htz.tmall.pojo.Category;
import org.junit.Test;

import java.util.List;

public class CategoryDaoImplTest {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Test
    public void getByCid() {
    }

    @Test
    public void update() {
    }

    @Test
    public void queryForPage() {
        List<Category> categories = categoryDao.queryForPage(0, 5);
        System.out.println(categories);
    }

    @Test
    public void add() {
    }

    @Test
    public void getTotalNumber() {
    }
}