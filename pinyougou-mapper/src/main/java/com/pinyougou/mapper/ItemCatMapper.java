package com.pinyougou.mapper;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import com.pinyougou.pojo.ItemCat;

import java.util.List;

/**
 * ItemCatMapper 数据访问接口
 * @date 2019-02-27 09:55:07
 * @version 1.0
 */
public interface ItemCatMapper extends Mapper<ItemCat>{

    @Select("SELECT * FROM `tb_item_cat` WHERE parent_id = #{parentId}")
    List<ItemCat> findItemCatByParentId(Long parentId);
}