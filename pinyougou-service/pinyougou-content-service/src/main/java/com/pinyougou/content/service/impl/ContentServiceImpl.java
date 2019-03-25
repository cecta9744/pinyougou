package com.pinyougou.content.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.ContentMapper;
import com.pinyougou.pojo.Content;
import com.pinyougou.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
/**
 * ContentServiceImpl 服务接口实现类
 * @date 2019-03-07 11:05:21
 * @version 1.0
 */
@Service(interfaceName = "com.pinyougou.service.ContentService")
@Transactional
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentMapper contentMapper;
	@Autowired
	private RedisTemplate redisTemplate;

	/** 添加方法 */
	public void save(Content content){
		try {
			contentMapper.insertSelective(content);

			// 删除广告缓存
			redisTemplate.delete("content");
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 修改方法 */
	public void update(Content content){
		try {
			contentMapper.updateByPrimaryKeySelective(content);

			// 删除广告缓存
			redisTemplate.delete("content");
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id删除 */
	public void delete(Serializable id){
		try {
			contentMapper.deleteByPrimaryKey(id);

			// 删除广告缓存
			redisTemplate.delete("content");
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 批量删除 */
	public void deleteAll(Serializable[] ids){
		try {
			// 创建示范对象
			Example example = new Example(Content.class);
			// 创建条件对象
			Example.Criteria criteria = example.createCriteria();
			// 创建In条件
			criteria.andIn("id", Arrays.asList(ids));
			// 根据示范对象删除
			contentMapper.deleteByExample(example);

			// 删除广告缓存
			redisTemplate.delete("content");
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据主键id查询 */
	public Content findOne(Serializable id){
		try {
			return contentMapper.selectByPrimaryKey(id);
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 查询全部 */
	public List<Content> findAll(){
		try {
			return contentMapper.selectAll();
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 多条件分页查询 */
	public PageResult findByPage(Content content, int page, int rows){
		try {
			PageInfo<Content> pageInfo = PageHelper.startPage(page, rows)
				.doSelectPageInfo(new ISelect() {
					@Override
					public void doSelect() {
						contentMapper.selectAll();
					}
				});
			return new PageResult(pageInfo.getTotal(), pageInfo.getList());
		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/** 根据广告分类id查询广告内容 */
	public List<Content> findContentByCategoryId(Long categoryId){

		List<Content> contentList = null;
		/** ######### 从Redis数据库获取缓存数据 ######### */
		try {
			contentList = (List<Content>) redisTemplate.boundValueOps("content").get();
			if (contentList != null && contentList.size() > 0){
				System.out.println(" ######### 从Redis数据库获取缓存数据 #########");
				return contentList;
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}

		try {
			// SELECT * FROM tb_content WHERE category_id = 1 AND STATUS = 1 ORDER BY sort_order ASC
			// 创建Example对象
			Example example = new Example(Content.class); // select * from tb_content

			// 创建条件对象
			Example.Criteria criteria = example.createCriteria();
			// category_id = 1
			criteria.andEqualTo("categoryId", categoryId);
			// STATUS = 1
			criteria.andEqualTo("status", 1);

			// ORDER BY sort_order ASC
			example.orderBy("sortOrder").asc();
			// 条件查询
			contentList =  contentMapper.selectByExample(example);

			/** ######### 把广告数据存储到Redis数据库(操作Redis数据库的代码放到try{}catch{}中，
			 * 	不要影响原来的业务) ######### */
			try{
				redisTemplate.boundValueOps("content").set(contentList);
				System.out.println("=====把广告数据存储到Redis数据库=====");
			}catch (Exception ex){
				ex.printStackTrace();
			}
			return contentList;

		}catch (Exception ex){
			throw new RuntimeException(ex);
		}
	}

}