package com.pinyougou.service;

import com.pinyougou.pojo.Item;
import java.util.List;
import java.io.Serializable;
/**
 * ItemService 服务接口
 * @date 2019-02-27 10:03:32
 * @version 1.0
 */
public interface ItemService {

	/** 添加方法 */
	void save(Item item);

	/** 修改方法 */
	void update(Item item);

	/** 根据主键id删除 */
	void delete(Serializable id);

	/** 批量删除 */
	void deleteAll(Serializable[] ids);

	/** 根据主键id查询 */
	Item findOne(Serializable id);

	/** 查询全部 */
	List<Item> findAll();

	/** 多条件分页查询 */
	List<Item> findByPage(Item item, int page, int rows);

}