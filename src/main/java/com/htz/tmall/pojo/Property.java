package com.htz.tmall.pojo;

/**
 * 属性类
 */
public class Property {
    private Integer id;
    private Integer cid;
    private String name;
    private Category category; //属性与分类多对一

    public Property(){
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
