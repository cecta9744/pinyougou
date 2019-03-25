package com.pinyougou.search.listener;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.pojo.Item;
import com.pinyougou.service.GoodsService;
import com.pinyougou.service.ItemSearchService;
import com.pinyougou.solr.SolrItem;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 消息监听器(把SKU商品数据同步到索引库 添加或修改)
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-15<p>
 */
public class ItemMessageListener implements SessionAwareMessageListener<ObjectMessage>{

    @Reference(timeout = 10000)
    private GoodsService goodsService;
    @Reference(timeout = 10000)
    private ItemSearchService itemSearchService;

    @Override
    public void onMessage(ObjectMessage objectMessage, Session session) throws JMSException {
        try{
            System.out.println("==========ItemMessageListener==========");
            // 1. 获取消息内容
            Long[] goodsIds = (Long[])objectMessage.getObject();
            System.out.println("goodsIds:" + Arrays.toString(goodsIds));

            // 2. 根据多个goodsId查询SKU数据
            List<Item> itemList = goodsService.findItemByGoodsId(goodsIds);

            // 2.1 将List<Item> 转化成 List<SolrItem>
            List<SolrItem> solrItems = new ArrayList<>();
            for (Item item1 : itemList) {
                // item1 转化成 SolrItem
                SolrItem solrItem = new SolrItem();
                solrItem.setId(item1.getId());
                solrItem.setTitle(item1.getTitle());
                solrItem.setPrice(item1.getPrice());
                solrItem.setImage(item1.getImage());
                solrItem.setGoodsId(item1.getGoodsId());
                solrItem.setCategory(item1.getCategory());
                solrItem.setBrand(item1.getBrand());
                solrItem.setSeller(item1.getSeller());
                solrItem.setUpdateTime(item1.getUpdateTime());

                // 获取spec列的数据 {"网络":"联通4G","机身内存":"64G"}
                String spec = item1.getSpec();
                // 把spec json字符串转化成Map集合
                Map<String, String> specMap = JSON.parseObject(spec, Map.class);
                // 设置动态域的数据
                solrItem.setSpecMap(specMap);
                solrItems.add(solrItem);
            }

            // 3. 调用搜索服务接口完成SKU商品索引数据的同步
            itemSearchService.saveOrUpdate(solrItems);

            // 提交事务
            session.commit();
        }catch (Exception ex){
            // 回滚事务
            session.rollback();
            throw new RuntimeException(ex);
        }
    }
}
