package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.TypeTemplate;
import com.pinyougou.service.TypeTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 类型模板控制器
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-02<p>
 */
@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

    @Reference(timeout = 10000)
    private TypeTemplateService typeTemplateService;

    /** 多条件分页查询类型模板 */
    @GetMapping("/findByPage")
    public PageResult findByPage(TypeTemplate typeTemplate,
                                 Integer page, Integer rows){
        // GET请求中文转码
        try{
            if (StringUtils.isNoneBlank(typeTemplate.getName())){
                typeTemplate.setName(new String(typeTemplate.
                        getName().getBytes("ISO8859-1"), "UTF-8"));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return typeTemplateService.findByPage(typeTemplate, page, rows);
    }

    /** 添加 */
    @PostMapping("/save")
    public boolean save(@RequestBody TypeTemplate typeTemplate){
        try{
            typeTemplateService.save(typeTemplate);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /** 修改 */
    @PostMapping("/update")
    public boolean update(@RequestBody TypeTemplate typeTemplate){
        try{
            typeTemplateService.update(typeTemplate);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /** 删除 */
    @GetMapping("/delete")
    public boolean delete(Long[] ids){
        try{
            typeTemplateService.deleteAll(ids);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
