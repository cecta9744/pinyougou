package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Content;
import com.pinyougou.service.ContentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * ContentController 控制器类
 * @date 2019-03-07 11:10:56
 * @version 1.0
 */
@RestController
@RequestMapping("/content")
public class ContentController {

	@Reference(timeout = 10000)
	private ContentService contentService;

	/** 查询全部方法 */
	@GetMapping("/findAll")
	public List<Content> findAll() {
		return contentService.findAll();
	}

	/** 多条件分页查询方法 */
	@GetMapping("/findByPage")
	public PageResult findByPage(Content content,
								 Integer page, Integer rows) {
		try {
			return contentService.findByPage(content, page, rows);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	/** 根据主键id查询方法 */
	@GetMapping("/findOne")
	public Content findOne(Long id) {
		try {
			return contentService.findOne(id);
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return null;
	}

	/** 添加方法 */
	@PostMapping("/save")
	public boolean save(@RequestBody Content content) {
		try {
			contentService.save(content);
			return true;
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

	/** 修改方法 */
	@PostMapping("/update")
	public boolean update(@RequestBody Content content) {
		try {
			contentService.update(content);
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
			contentService.deleteAll(ids);
			return true;
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return false;
	}

}
