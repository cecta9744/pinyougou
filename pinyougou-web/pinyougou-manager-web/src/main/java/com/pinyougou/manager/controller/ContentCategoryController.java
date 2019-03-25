package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.ContentCategory;
import com.pinyougou.service.ContentCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * ContentCategoryController 控制器类
 * @date 2019-03-07 11:10:56
 * @version 1.0
 */
@RestController
@RequestMapping("/contentCategory")
public class ContentCategoryController {

	@Reference(timeout = 10000)
	private ContentCategoryService contentCategoryService;

	/** 查询全部方法 */
	@GetMapping("/findAll")
	public List<ContentCategory> findAll() {
		return contentCategoryService.findAll();
	}

	/** 多条件分页查询方法 */
	@GetMapping("/findByPage")
	public PageResult findByPage(ContentCategory contentCategory,
								 Integer page, Integer rows) {
		try {
			return contentCategoryService.findByPage(contentCategory, page, rows);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	/** 根据主键id查询方法 */
	@GetMapping("/findOne")
	public ContentCategory findOne(Long id) {
		try {
			return contentCategoryService.findOne(id);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	/** 添加方法 */
	@PostMapping("/save")
	public boolean save(@RequestBody ContentCategory contentCategory) {
		try {
			contentCategoryService.save(contentCategory);
			return true;
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	/** 修改方法 */
	@PostMapping("/update")
	public boolean update(@RequestBody ContentCategory contentCategory) {
		try {
			contentCategoryService.update(contentCategory);
			return true;
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	/** 删除方法 */
	@GetMapping("/delete")
	public boolean delete(Long[] ids) {
		try {
			contentCategoryService.deleteAll(ids);
			return true;
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

}
