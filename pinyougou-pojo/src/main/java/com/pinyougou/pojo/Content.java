package com.pinyougou.pojo;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 广告实体
 */
@Table(name="tb_content")
public class Content implements Serializable{
   
	private static final long serialVersionUID = 6081205748121801261L;
	/** 主键id */
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	/** 广告分类id */
	@Column(name="category_id")
    private Long categoryId;
	/** 广告标题 */
	@Column(name="title")
    private String title;
	/** 广告链接 */
	@Column(name="url")
    private String url;
	/** 图片地址 */
	@Column(name="pic")
    private String pic;
	/** 状态 */
	@Column(name="status")
    private String status = "0";
	/** 排序 */
	@Column(name="sort_order")
    private Integer sortOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic == null ? null : pic.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}