package com.pinyougou.mapper;

import com.pinyougou.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * BrandMapper 数据访问接口
 * @date 2019-02-27 09:55:07
 * @version 1.0
 */
public interface BrandMapper extends Mapper<Brand>{

    /** 多条件查询品牌 */
    List<Brand> findAll(Brand brand);

    /** 批量删除品牌 */
    void deleteAll(@Param("ids") Serializable[] ids);

    /** 查询全部品牌(id与name) */
    @Select("select id, name as text from tb_brand")
    List<Map<String,Object>> findAllByIdAndName();
}