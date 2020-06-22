package com.htz.tmall.pojo;

/**
 * 产品图片类
 */
public class ProductImage {
    private Integer id;
    private Integer pid;
    private String type;  //图片类型(单个图片，详情图片)
    private Product product; //产品图片与产品多对一

    public ProductImage(){
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
