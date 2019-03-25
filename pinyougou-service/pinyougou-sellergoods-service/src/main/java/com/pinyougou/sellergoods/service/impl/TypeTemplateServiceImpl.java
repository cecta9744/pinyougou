package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SpecificationOptionMapper;
import com.pinyougou.mapper.TypeTemplateMapper;
import com.pinyougou.pojo.SpecificationOption;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 类型模板服务接口实现类
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-02<p>
 */
@Service(interfaceName = "com.pinyougou.service.TypeTemplateService")
@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @Autowired
    private TypeTemplateMapper typeTemplateMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    @Override
    public void save(TypeTemplate typeTemplate) {
        try{
            typeTemplateMapper.insertSelective(typeTemplate);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(TypeTemplate typeTemplate) {
        try{
            typeTemplateMapper.updateByPrimaryKeySelective(typeTemplate);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public void deleteAll(Serializable[] ids) {
        try{
            typeTemplateMapper.deleteAll(ids);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public TypeTemplate findOne(Serializable id) {
        return typeTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TypeTemplate> findAll() {
        return null;
    }

    @Override
    public PageResult findByPage(TypeTemplate typeTemplate, int page, int rows) {
        try{
            // 开始分页
            PageInfo<TypeTemplate> pageInfo = PageHelper.startPage(page, rows)
                    .doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    typeTemplateMapper.findAll(typeTemplate);
                }
            });
            return new PageResult(pageInfo.getTotal(), pageInfo.getList());
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /** 查询规格选项数据 */
    public List<Map> findSpecByTemplateId(Long id){
        try{
            // 1. 根据id查询类型模板对象
            TypeTemplate typeTemplate = findOne(id);

            // 2. 获取spec_ids [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
            String specIds = typeTemplate.getSpecIds();

            // 3. 把json数组的字符串，转化成List<Map>
            // JSON.parseObject() : {}
            // JSON.parseArray() : [{},{}]
            List<Map> specList = JSON.parseArray(specIds, Map.class);

            // 4. 迭代List集合
            for (Map map : specList) {
                // map : {"id":27,"text":"网络"}
                // 5. 根据spec_id到规格选项表查询数据

                Long specId = Long.valueOf(map.get("id").toString());
                // 创建对象封装查询条件
                SpecificationOption so = new SpecificationOption();
                so.setSpecId(specId);
                // SELECT * FROM tb_specification_option WHERE spec_id = 27
                List<SpecificationOption> options = specificationOptionMapper.select(so);

                map.put("options", options);

            }
            return specList;

        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
