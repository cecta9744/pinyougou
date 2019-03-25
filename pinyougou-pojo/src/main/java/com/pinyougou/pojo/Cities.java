package com.pinyougou.pojo;

import java.io.Serializable;

import javax.persistence.*;

/** 城市实体 */
@Table(name="tb_cities")
public class Cities implements Serializable{
 
	/** 主键id */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	/** 城市编号 */
	@Column(name="cityid")
    private String cityId;
	/** 城市名称 */
	@Column(name="city")
    private String city;
	/** 省份编号 */
	@Column(name="provinceid")
    private String provinceId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }
}