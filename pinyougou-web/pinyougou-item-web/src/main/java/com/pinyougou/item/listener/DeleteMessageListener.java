package com.pinyougou.item.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.io.File;
import java.util.Arrays;

/**
 * 消息监听器(删除SPU商品的静态页面)
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-15<p>
 */
public class DeleteMessageListener implements SessionAwareMessageListener<ObjectMessage>{

    @Value("${pageDir}")
    private String pageDir;

    @Override
    public void onMessage(ObjectMessage objectMessage, Session session) throws JMSException {
        try{
            System.out.println("======DeleteMessageListener======");
            // 1. 接收消息内容
            Long[] goodsIds = (Long[])objectMessage.getObject();
            System.out.println("goodsIds: " + Arrays.toString(goodsIds));

            // 2. 删除该SPU商品的静态页面
            for (Long goodsId : goodsIds) {
                File file = new File(pageDir + goodsId + ".html");
                if (file.exists()){
                    file.delete();
                }
            }

        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
