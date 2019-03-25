package com.pinyougou.pojo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 规格选项实体类
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2017年12月7日 下午3:32:27
 * @version 1.0
 */
@Table(name="tb_specification_option")
public class SpecificationOption implements Serializable{
    
	private static final long serialVersionUID = -6501671350315921193L;
	/** 编号 主键 */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	/** 规格选项名称 */
	@Column(name="option_name")
    private String optionName;
    /** 规格id */
	@Column(name="spec_id")
    private Long specId;
    /** 排序 */
	@Column(name="orders")
    private Integer orders;
    
    /** setter and getter method */
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOptionName() {
        return optionName;
    }
    public void setOptionName(String optionName) {
        this.optionName = optionName == null ? null : optionName.trim();
    }
    public Long getSpecId() {
        return specId;
    }
    public void setSpecId(Long specId) {
        this.specId = specId;
    }
    public Integer getOrders() {
        return orders;
    }
    public void setOrders(Integer orders) {
        this.orders = orders;
    }
}