package com.pinyougou.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;
/**
 * 商品实体
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2017年12月19日 下午11:44:19
 * @version 1.0
 */
@Table(name="tb_goods")
public class Goods implements Serializable{

	private static final long serialVersionUID = -3888154864571208139L;
	/** 主键 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	/** 商家id */
	@Column(name="seller_id")
    private String sellerId;
    /** SPU名称 */
	@Column(name="goods_name")
    private String goodsName;
    /** 默认SKU */
	@Column(name="default_item_id")
    private Long defaultItemId;
    /** 状态 */
	@Column(name="audit_status")
    private String auditStatus;
    /** 是否上架 */
	@Column(name="is_marketable")
    private String isMarketable;
    /** 品牌id */
	@Column(name="brand_id")
    private Long brandId;
    /** 副标题 */
	@Column(name="caption")
    private String caption;
    /** 一级分类 */
	@Column(name="category1_id")
    private Long category1Id;
    /** 二级分类 */
	@Column(name="category2_id")
    private Long category2Id;
    /** 三级分类 */
	@Column(name="category3_id")
    private Long category3Id;
    /** 小图 */
	@Column(name="small_pic")
    private String smallPic;
    /** 商城价 */
	@Column(name="price")
    private BigDecimal price;
    /** 分类模版id */
	@Column(name="type_template_id")
    private Long typeTemplateId;
    /** 是否启用规格 */
	@Column(name="is_enable_spec")
    private String isEnableSpec;
    /** 是否删除 */
	@Column(name="is_delete")
    private String isDelete;
    /** 商品描述 */
	@Transient
    private GoodsDesc goodsDesc;
    /** 商品SKU列表 */
	@Transient
    private List<Item> items;
    
    /** setter and getter method */
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSellerId() {
        return sellerId;
    }
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }
    public String getGoodsName() {
        return goodsName;
    }
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }
    public Long getDefaultItemId() {
        return defaultItemId;
    }
    public void setDefaultItemId(Long defaultItemId) {
        this.defaultItemId = defaultItemId;
    }
    public String getAuditStatus() {
        return auditStatus;
    }
    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }
    public String getIsMarketable() {
        return isMarketable;
    }
    public void setIsMarketable(String isMarketable) {
        this.isMarketable = isMarketable == null ? null : isMarketable.trim();
    }
    public Long getBrandId() {
        return brandId;
    }
    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption == null ? null : caption.trim();
    }
    public Long getCategory1Id() {
        return category1Id;
    }
    public void setCategory1Id(Long category1Id) {
        this.category1Id = category1Id;
    }
    public Long getCategory2Id() {
        return category2Id;
    }
    public void setCategory2Id(Long category2Id) {
        this.category2Id = category2Id;
    }
    public Long getCategory3Id() {
        return category3Id;
    }
    public void setCategory3Id(Long category3Id) {
        this.category3Id = category3Id;
    }
    public String getSmallPic() {
        return smallPic;
    }
    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic == null ? null : smallPic.trim();
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Long getTypeTemplateId() {
        return typeTemplateId;
    }
    public void setTypeTemplateId(Long typeTemplateId) {
        this.typeTemplateId = typeTemplateId;
    }
    public String getIsEnableSpec() {
        return isEnableSpec;
    }
    public void setIsEnableSpec(String isEnableSpec) {
        this.isEnableSpec = isEnableSpec == null ? null : isEnableSpec.trim();
    }
    public String getIsDelete() {
        return isDelete;
    }
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }
	public GoodsDesc getGoodsDesc() {
		return goodsDesc;
	}
	public void setGoodsDesc(GoodsDesc goodsDesc) {
		this.goodsDesc = goodsDesc;
	}
	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
}