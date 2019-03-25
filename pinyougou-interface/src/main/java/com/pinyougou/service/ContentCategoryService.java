package com.pinyougou.service;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.ContentCategory;
import java.util.List;
import java.io.Serializable;
/**
 * ContentCategoryService 服务接口
 * @date 2019-02-27 10:03:32
 * @version 1.0
 */
public interface ContentCategoryService {

	/** 添加方法 */
	void save(ContentCategory contentCategory);

	/** 修改方法 */
	void update(ContentCategory contentCategory);

	/** 根据主键id删除 */
	void delete(Serializable id);

	/** 批量删除 */
	void deleteAll(Serializable[] ids);

	/** 根据主键id查询 */
	ContentCategory findOne(Serializable id);

	/** 查询全部 */
	List<ContentCategory> findAll();

	/** 多条件分页查询 */
	PageResult findByPage(ContentCategory contentCategory, int page, int rows);

}