package com.pinyougou.search.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.service.ItemSearchService;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.util.Arrays;

/**
 * 消息监听器(从索引库删除SKU商品的索引)
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-15<p>
 */
public class DeleteMessageListener implements SessionAwareMessageListener<ObjectMessage>{

    @Reference(timeout = 10000)
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(ObjectMessage objectMessage, Session session) throws JMSException {
        try{
            System.out.println("==========DeleteMessageListener==========");
            // 1. 获取消息内容
            Long[] goodsIds = (Long[])objectMessage.getObject();
            System.out.println("goodsIds:" + Arrays.toString(goodsIds));

            // 2. 调用搜索服务接口，删除索引库中的索引
            itemSearchService.delete(goodsIds);

            // 提交事务
            session.commit();
        }catch (Exception ex){
            // 回滚事务
            session.rollback();
            throw new RuntimeException(ex);
        }
    }
}
