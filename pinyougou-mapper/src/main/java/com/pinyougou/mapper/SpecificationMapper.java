package com.pinyougou.mapper;

import com.pinyougou.pojo.Specification;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * SpecificationMapper 数据访问接口
 * @date 2019-02-27 09:55:07
 * @version 1.0
 */
public interface SpecificationMapper extends Mapper<Specification>{


    /** 多条件查询规格 */
    List<Specification> findAll(Specification specification);

    /** 查询规格(id与name) */
    @Select("select id, spec_name as text from tb_specification")
    List<Map<String,Object>> findAllByIdAndName();
}