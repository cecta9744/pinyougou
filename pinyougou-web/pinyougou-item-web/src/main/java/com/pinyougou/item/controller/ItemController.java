package com.pinyougou.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.service.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * 商品详情控制器
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-13<p>
 */
@Controller
public class ItemController {

    @Reference(timeout = 10000)
    private GoodsService goodsService;
    /**
     * http://item.pinyougou.com/5089253.html
     * http://item.pinyougou.com/SPU的id.html
     * 获取请求URL路径上的参数
     * http://item.pinyougou.com
     */
    @GetMapping("/{goodsId}")
    public String getGoods(@PathVariable("goodsId")Long goodsId, Model model){
        System.out.println("goodsId:" + goodsId);

        // Model: 它就是FreeMarker的数据模型
        Map<String,Object> dataModel = goodsService.getGoods(goodsId);
        model.addAllAttributes(dataModel);

        return "item";
    }
}
