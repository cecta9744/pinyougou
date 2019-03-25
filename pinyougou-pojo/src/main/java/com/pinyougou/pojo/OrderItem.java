package com.pinyougou.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

@Table(name="tb_order_item")
public class OrderItem implements Serializable{
  
	private static final long serialVersionUID = -1486894002218647239L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	@Column(name="item_id")
    private Long itemId;
	@Column(name="goods_id")
    private Long goodsId;
	@Column(name="order_id")
    private Long orderId;
	@Column(name="title")
    private String title;
	@Column(name="price")
    private BigDecimal price;
	@Column(name="num")
    private Integer num;
	@Column(name="total_fee")
    private BigDecimal totalFee;
	@Column(name="pic_path")
    private String picPath;
	@Column(name="seller_id")
    private String sellerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath == null ? null : picPath.trim();
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }
}