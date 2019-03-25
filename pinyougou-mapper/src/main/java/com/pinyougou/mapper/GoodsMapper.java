package com.pinyougou.mapper;

import com.pinyougou.pojo.Goods;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * GoodsMapper 数据访问接口
 * @date 2019-02-27 09:55:07
 * @version 1.0
 */
public interface GoodsMapper extends Mapper<Goods>{


    /** 多条件查询商品 */
    List<Map<String,Object>> findAll(Goods goods);

    /** 修改商品的状态码(通用的修改方法) */
    void updateStatus(@Param("columnName")String columnName,
                      @Param("ids") Long[] ids,
                      @Param("status") String status);
}