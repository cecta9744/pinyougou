package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 品牌控制器
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-02-25<p>
 */
@RestController // @Controller + @ResponseBody
@RequestMapping("/brand")
public class BrandController {

    /**
     * 配置引用服务
     * timeout : 超时毫秒数 1000毫秒
     * */
    @Reference(timeout = 10000)
    private BrandService brandService;

    // 分页查询品牌
    @GetMapping("/findByPage")
    public PageResult findByPage(Brand brand, Integer page, Integer rows){
        try {
            // GET请求转码
            if (brand != null && StringUtils.isNoneBlank(brand.getName())) {
                brand.setName(new String(brand.getName().getBytes("ISO8859-1"), "UTF-8"));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        // {total : 100, rows : [{},{},{}]} {}: Map|实体类 []: List
        return brandService.findByPage(brand, page, rows);
    }

    /** 添加品牌 */
    @PostMapping("/save")
    public boolean save(@RequestBody Brand brand){
        try{
            brandService.save(brand);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /** 修改品牌 */
    @PostMapping("/update")
    public boolean update(@RequestBody Brand brand){
        try{
            brandService.update(brand);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /** 修改品牌 */
    @GetMapping("/delete")// ids=1,2,3
    public boolean delte(Long[] ids){
        try{
            brandService.deleteAll(ids);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /** 查询全部品牌 */
    @GetMapping("/findBrandList")
    public List<Map<String,Object>> findBrandList(){
        // [{id : 1, text : '华为'},{id : 2, text : '小米'}]
        // List<Brand> : [{id : 1, name : '', firstChar : ''},{}]
        return brandService.findAllByIdAndName();
    }
}
