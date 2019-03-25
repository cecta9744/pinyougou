package com.pinyougou.solr.util;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.ItemMapper;
import com.pinyougou.pojo.Item;
import com.pinyougou.solr.SolrItem;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据导入(把tb_item表中的数据导入到Solr服务器的collection1索引库)
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-10<p>
 */
@Component
public class SolrUtils {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrTemplate solrTemplate;

    /** 把数据导入到Solr服务器的索引库 */
    public void importDataToSolr(){

        // 1. 查询SKU商品数据
        Item item = new Item();
        // 正常的商品
        item.setStatus("1");
        // 条件查询 select * from tb_item where status = 1
        List<Item> itemList = itemMapper.select(item);

        // 定义索引库数据相关的集合
        List<SolrItem> solrItems = new ArrayList<>();
        System.out.println("=========华丽分割线==========");
        for (Item item1 : itemList) {
            System.out.println(item1.getId() + "\t" + item1.getTitle());

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
        System.out.println("=========华丽分割线==========");

        /** 添加或修改索引库 */
        UpdateResponse updateResponse = solrTemplate.saveBeans(solrItems);
        if (updateResponse.getStatus() == 0){
            solrTemplate.commit();
        }else{
            solrTemplate.rollback();
        }

    }

    // web项目创建spring容器 web.xml配置
    // 普通的java项目创建spring容器 main
    public static void main(String[] args){
        // 创建Spring容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取SolrUtils
        SolrUtils solrUtils = ac.getBean(SolrUtils.class);
        solrUtils.importDataToSolr();
    }
}
