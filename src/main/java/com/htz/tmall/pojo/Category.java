package com.htz.tmall.pojo;

import java.util.List;

/**
 * 产品分类表对应的实体类
 */
public class Category {
    private Integer id;
    private String name;

    // 分类与产品是一对多的关系
    private List<Product> products;

    //为了首页分类显示而定义的集合
    //一个分类会对应多行产品，而一行产品里又有多个产品记录。
    private List<List<Product>> productsByRow;

    public Category() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                '}';
    }
}
