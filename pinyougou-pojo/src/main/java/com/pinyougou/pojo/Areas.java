package com.pinyougou.pojo;

import java.io.Serializable;

import javax.persistence.*;

/** 区域实体*/
@Table(name="tb_areas")
public class Areas implements Serializable{

    /** 主键id */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	/** 区域id */
	@Column(name="areaid")
    private String areaId;
	/** 区域名称 */
	@Column(name="area")
    private String area;
	/** 城市id */
	@Column(name="cityid")
    private String cityId;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getAreaId() {
        return areaId;
    }
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getCityId() {
        return cityId;
    }
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}