package com.pinyougou.pojo;

import java.io.Serializable;

import javax.persistence.*;

/** 省份实体 */
@Table(name="tb_provinces")
public class Provinces implements Serializable{
    
	private static final long serialVersionUID = -4527956140012506847L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name="provinceid")
    private String provinceId;
	@Column(name="province")
    private String province;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}