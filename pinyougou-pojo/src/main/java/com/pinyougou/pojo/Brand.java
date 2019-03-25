package com.pinyougou.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 品牌实体类
 * @author LEE.SIU.WAH
 * @email lixiaohua7@163.com
 * @date 2017年12月1日 下午4:59:52
 * @version 1.0
 */
@Table(name="tb_brand")
public class Brand implements Serializable{
	private static final long serialVersionUID = -8315881228799842049L;
	/** 主键ID */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	/** 品牌名称 */
	@Column(name="name")
    private String name;
    /** 品牌首字母 */
	@Column(name="first_char")
    private String firstChar;
    /** setter and getter method */
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
    public String getFirstChar() {
        return firstChar;
    }
    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar == null ? null : firstChar.trim();
    }
}