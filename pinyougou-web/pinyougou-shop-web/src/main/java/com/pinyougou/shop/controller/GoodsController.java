package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Goods;
import com.pinyougou.service.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * 商品控制器
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-04<p>
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference(timeout = 10000)
    private GoodsService goodsService;

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Destination solrQueue;
    @Autowired
    private Destination solrDeleteQueue;
    @Autowired
    private Destination pageTopic;
    @Autowired
    private Destination pageDeleteTopic;

    /** 添加商品 */
    @PostMapping("/save")
    public boolean save(@RequestBody Goods goods){
        try{
            // 获取登录用户名(商家id)
            String sellerId = SecurityContextHolder.getContext()
                    .getAuthentication().getName();
            // 设置商品关联的商家id
            goods.setSellerId(sellerId);
            goodsService.save(goods);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }

    /** 多条件分页查询商家的商品 */
    @GetMapping("/findByPage")
    public PageResult findByPage(Goods goods, Integer page, Integer rows){
        // 获取登录用户名
        String sellerId = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        // 设置商家id
        goods.setSellerId(sellerId);

        /** GET请求中文转码 */
        if (StringUtils.isNoneBlank(goods.getGoodsName())) {
            try {
                goods.setGoodsName(new String(goods
                        .getGoodsName().getBytes("ISO8859-1"), "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return goodsService.findByPage(goods, page, rows);
    }

    /** 商品上下架(修改上下架状态) */
    @GetMapping("/updateMarketable")
    public boolean updateMarketable(Long[] ids, String status){
        try{
            goodsService.updateStatus("is_marketable", ids, status);

            // 判断上下架
            if ("1".equals(status)){ // 上架

                // 发送消息到消息中间件(创建SKU商品的索引)
                jmsTemplate.send(solrQueue, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createObjectMessage(ids);
                    }
                });


                // 发送消息到消息中间件(生成SPU商品的静态页面)
                for (Long id : ids) {
                    jmsTemplate.send(pageTopic, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage(id.toString());
                        }
                    });
                }


            }else{ // 下架

                // 发送消息到消息中间件(删除SKU商品的索引)
                jmsTemplate.send(solrDeleteQueue, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createObjectMessage(ids);
                    }
                });

                // 发送消息到消息中间件(删除SPU商品的静态页面)
                jmsTemplate.send(pageDeleteTopic, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createObjectMessage(ids);
                    }
                });
            }
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
