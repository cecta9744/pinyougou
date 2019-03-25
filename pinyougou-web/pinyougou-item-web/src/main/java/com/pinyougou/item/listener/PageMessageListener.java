package com.pinyougou.item.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.service.GoodsService;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * 消息监听器(生成SPU商品的静态页面)
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-15<p>
 */
public class PageMessageListener implements SessionAwareMessageListener<TextMessage>{

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Reference(timeout = 10000)
    private GoodsService goodsService;
    @Value("${pageDir}")
    private String pageDir;

    @Override
    public void onMessage(TextMessage textMessage, Session session) throws JMSException {
        try{
            System.out.println("======PageMessageListener======");
            // 1. 接收消息内容
            String goodsId = textMessage.getText();
            System.out.println("goodsId: " + goodsId);

            // 2. 生成该SPU商品的静态页面(把动态页面转化成静态页面) item.ftl
            // 2.1 获取item.ftl模板文件对应的模板对象
            Template template = freeMarkerConfigurer.
                    getConfiguration().getTemplate("item.ftl");

            // 2.2 创建数据模型
            Map<String,Object> dataModel = goodsService.getGoods(Long.valueOf(goodsId));

            // 创建输出流
            OutputStreamWriter writer = new OutputStreamWriter(new
                    FileOutputStream(pageDir + goodsId + ".html"), "UTF-8");
            // 2.3 填充模板，输出静态页面
            template.process(dataModel, writer);

            writer.flush();
            writer.close();

        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
