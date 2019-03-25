package com.pinyougou.solr;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Dynamic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * SKU商品实体类(配置实体类与文档之间映射关系)
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-10<p>
 */
public class SolrItem implements Serializable {

    @Field("id")
    private Long id;
    @Field("title")
    private String title;
    @Field("price")
    private double price;
    @Field("image")
    private String image;
    @Field("goodsId")
    private Long goodsId;
    @Field("category")
    private String category;
    @Field("brand")
    private String brand;
    @Field("seller")
    private String seller;
    @Field("updateTime")
    private Date updateTime;

    // 动态域 spec_*
    @Dynamic
    @Field("spec_*")
    private Map<String, String> specMap;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price.doubleValue();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Map<String, String> getSpecMap() {
        return specMap;
    }

    public void setSpecMap(Map<String, String> specMap) {
        this.specMap = specMap;
    }
}
