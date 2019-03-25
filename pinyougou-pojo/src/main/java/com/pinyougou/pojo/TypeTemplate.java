package com.pinyougou.pojo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 类型模版实体
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2017年12月11日 下午5:36:41
 * @version 1.0
 */
@Table(name="tb_type_template")
public class TypeTemplate implements Serializable{
    
	private static final long serialVersionUID = 1052190869702363112L;
	/** 类型模版编号 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	/** 类型模版名称 */
	@Column(name="name")
    private String name;
    /** 关联规格（json格式） */
	@Column(name="spec_ids")
    private String specIds;
    /** 关联品牌（json格式） */
	@Column(name="brand_ids")
    private String brandIds;
    /** 扩展属性 */
	@Column(name="custom_attribute_items")
    private String customAttributeItems;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    public String getSpecIds() {
        return specIds;
    }
	public void setSpecIds(String specIds) {
		this.specIds = specIds;
	}
	public String getBrandIds() {
	    return brandIds;
	}
	public void setBrandIds(String brandIds) {
		this.brandIds = brandIds;
	}
	public String getCustomAttributeItems() {
	    return customAttributeItems;
	}
	public void setCustomAttributeItems(String customAttributeItems) {
		this.customAttributeItems = customAttributeItems;
	}
}