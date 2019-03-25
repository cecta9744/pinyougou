package com.pinyougou.mapper;

import com.pinyougou.pojo.TypeTemplate;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.io.Serializable;
import java.util.List;

/**
 * TypeTemplateMapper 数据访问接口
 * @date 2019-02-27 09:55:07
 * @version 1.0
 */
public interface TypeTemplateMapper extends Mapper<TypeTemplate>{

    /** 多条件查询类型模板 */
    List<TypeTemplate> findAll(@Param("typeTemplate") TypeTemplate typeTemplate);

    /** 批量删除 */
    void deleteAll(Serializable[] ids);
}