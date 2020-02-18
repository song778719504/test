package com.hh.demo.entity.VO;

import com.hh.demo.entity.pojo.Product;
import com.hh.demo.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

public class ProductDateVo {

    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImages;
    private String detail;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private String createTime;
    private String updateTime;


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

    public String getSubImages() {
        return subImages;
    }

    public void setSubImages(String subImages) {
        this.subImages = subImages;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public ProductDateVo product2vo(Product product){
        this.setCategoryId(product.getCategoryId());
        this.setCreateTime(DateUtils.date2Str(product.getCreateTime()));
        this.setDetail(product.getDetail());
        this.setId(product.getId());
        this.setMainImage(product.getMainImage());
        this.setName(product.getName());
        this.setPrice(product.getPrice());
        this.setStatus(product.getStatus());
        this.setSubImages(product.getSubImages());
        this.setSubtitle(product.getSubtitle());
        this.setUpdateTime(DateUtils.date2Str(product.getUpdateTime()));
        this.setStock(product.getStock());
        return this;
    }

}
