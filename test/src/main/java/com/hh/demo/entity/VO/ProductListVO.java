package com.hh.demo.entity.VO;

import com.hh.demo.entity.pojo.Product;

import java.math.BigDecimal;
import java.util.List;

public class ProductListVO {

    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private  Integer status;
    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductListVO{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", mainImage='" + mainImage + '\'' +
                ", status=" + status +
                ", price=" + price +
                '}';
    }

    public ProductListVO change(Product product){
        this.setSubtitle(product.getSubtitle());
        this.setStatus(product.getStatus());
        this.setPrice(product.getPrice());
        this.setName(product.getName());
        this.setMainImage(product.getMainImage());
        this.setCategoryId(product.getCategoryId());
        this.setId(product.getId());
        return this;
    }

}









