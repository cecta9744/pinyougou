package com.pinyougou.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.service.ItemSearchService;
import com.pinyougou.solr.SolrItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.core.query.result.ScoredPage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索服务接口实现类
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-10<p>
 */
@Service(interfaceName = "com.pinyougou.service.ItemSearchService")
public class ItemSearchServiceImpl implements ItemSearchService{

    @Autowired
    private SolrTemplate solrTemplate;

    /**
     * 添加或修改索引库
     * @param solrItems
     */
    public void saveOrUpdate(List<SolrItem> solrItems){
        try{
            UpdateResponse updateResponse = solrTemplate.saveBeans(solrItems);
            if (updateResponse.getStatus() == 0){
                solrTemplate.commit();
            }else{
                solrTemplate.rollback();
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /**
     * 删除索引库中的索引数据
     * @param goodsIds
     */
    public void delete(Long[] goodsIds){
        try{
            // 创建查询对象
            Query query = new SimpleQuery();
            // 创建条件对象
            Criteria criteria = new Criteria("goodsId").in(Arrays.asList(goodsIds));
            // 添加条件
            query.addCriteria(criteria);
            // 删除
            UpdateResponse updateResponse = solrTemplate.delete(query);
            if (updateResponse.getStatus() == 0){
                solrTemplate.commit();
            }else{
                solrTemplate.rollback();
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /** 搜索方法 */
    @Override
    public Map<String, Object> search(Map<String, Object> params) {
        try{
            // 获取关键字
            String keywords = (String)params.get("keywords");

            // 获取当前页码
            Integer page = (Integer) params.get("page");
            if (page == null || page < 1){
                page = 1;
            }
            // 获取页大小
            Integer rows = (Integer) params.get("rows");
            if (rows == null || rows < 1){
                rows = 20;
            }

            // 判断关键字
            if (StringUtils.isNoneBlank(keywords)){ // 高亮分页查询
                // 创建高亮查询对象
                HighlightQuery highlightQuery = new SimpleHighlightQuery();
                // 添加查询条件(高亮)
                Criteria criteria = new Criteria("keywords").is(keywords);
                // 添加查询条件
                highlightQuery.addCriteria(criteria);

                // ------------ 高亮 ------------
                // 创建高亮选项对象
                HighlightOptions highlightOptions = new HighlightOptions();
                // 添加高亮的域(title)
                highlightOptions.addField("title");
                // 设置高亮格式器前缀
                highlightOptions.setSimplePrefix("<font color='red'>");
                // 设置高亮格式器后缀
                highlightOptions.setSimplePostfix("</font>");
                // 设置高亮选项对象
                highlightQuery.setHighlightOptions(highlightOptions);


                // ------------ 过滤 ------------
                // {keywords: "小米", category: "手机", brand: "苹果",
                // spec: {网络: "联通3G", 机身内存: "64G"}, price: "1000-1500"}
                // 1. 按商品分类过滤
                String category = (String)params.get("category");
                if (StringUtils.isNoneBlank(category)){
                    // 创建过滤查询对象
                    FilterQuery filterQuery = new SimpleFilterQuery(new Criteria("category").is(category));
                    // 添加过滤条件(不高亮)
                    highlightQuery.addFilterQuery(filterQuery);
                }

                // 2. 按商品品牌过滤
                String brand = (String)params.get("brand");
                if (StringUtils.isNoneBlank(brand)){
                    // 创建过滤查询对象
                    FilterQuery filterQuery = new SimpleFilterQuery(new Criteria("brand").is(brand));
                    // 添加过滤条件(不高亮)
                    highlightQuery.addFilterQuery(filterQuery);
                }


                // 3. 按商品规格过滤
                // spec: {网络: "联通3G", 机身内存: "64G"}
                Map<String,String> specMap = (Map<String,String>)params.get("spec");
                if (specMap != null && specMap.size() > 0){
                    // "spec_网络": "联通4G",
                    // "spec_机身内存": "64G",
                    for (String key : specMap.keySet()){
                        // 创建过滤查询对象 spec_网络 | spec_机身内存
                        FilterQuery filterQuery = new SimpleFilterQuery(new Criteria("spec_" + key)
                                .is(specMap.get(key)));
                        // 添加过滤条件(不高亮)
                        highlightQuery.addFilterQuery(filterQuery);
                    }
                }

                // 4. 按商品价格过滤
                String price = (String)params.get("price");
                if (StringUtils.isNoneBlank(price)){
                    // 得到价格区间的数组
                    String[] priceArr = price.split("-");
                    // 0-500、1000-2000、3000-*
                    // 判断价格数组中的第一个元素是否不为 零
                    if (!"0".equals(priceArr[0])){
                        // 创建过滤查询对象
                        FilterQuery filterQuery = new SimpleFilterQuery(new Criteria("price")
                                .greaterThanEqual(priceArr[0]));
                        // 添加过滤条件(不高亮)
                        highlightQuery.addFilterQuery(filterQuery);
                    }
                    // 判断价格数组中的第二个元素是否不为 星号
                    if (!"*".equals(priceArr[1])){
                        // 创建过滤查询对象
                        FilterQuery filterQuery = new SimpleFilterQuery(new Criteria("price")
                                .lessThanEqual(priceArr[1]));
                        // 添加过滤条件(不高亮)
                        highlightQuery.addFilterQuery(filterQuery);
                    }
                }


                // ------------ 分页 ------------
                // 设置分页起始记录数
                highlightQuery.setOffset((page - 1) * rows);
                // 设置页大小
                highlightQuery.setRows(rows);


                // ------------ 排序 -------------
                //  sortField : '', sortValue : ''
                String sortField = (String)params.get("sortField");
                String sortValue = (String)params.get("sortValue");
                if (StringUtils.isNoneBlank(sortField) && StringUtils.isNoneBlank(sortValue)) {
                    // 创建排序对象
                    Sort sort = new Sort("ASC".equals(sortValue) ?
                            Sort.Direction.ASC : Sort.Direction.DESC, sortField);
                    // 添加排序
                    highlightQuery.addSort(sort);
                }


                // 高亮分页查询，得到高亮分页对象
                HighlightPage<SolrItem> highlightPage = solrTemplate
                        .queryForHighlightPage(highlightQuery, SolrItem.class);

                // 获取高亮项集合
                List<HighlightEntry<SolrItem>> highlighted = highlightPage.getHighlighted();
                // 迭代高亮项集合
                for (HighlightEntry<SolrItem> highlightEntry : highlighted) {
                    // 获取文档对象对应的实体
                    SolrItem solrItem = highlightEntry.getEntity();

                    // 获取所有Field的高亮内容
                    List<HighlightEntry.Highlight> highlights = highlightEntry.getHighlights();
                    if (highlights != null && highlights.size() > 0){
                        // 获取标题的高亮内容
                        String title = highlights.get(0).getSnipplets().get(0).toString();
                        System.out.println(title);
                        // 设置标题的高亮内容
                        solrItem.setTitle(title);
                    }
                }

                System.out.println("总记录数：" + highlightPage.getTotalElements());
                // 获取分页数据
                List<SolrItem> solrItems = highlightPage.getContent();

                Map<String, Object> data = new HashMap<>();
                // 分页数据
                data.put("rows", solrItems);
                // 总记录数
                data.put("total", highlightPage.getTotalElements());
                // 总页数
                data.put("totalPages", highlightPage.getTotalPages());
                return data;

            }else{ // 简单分页查询

                // 创建简单查询对象
                SimpleQuery simpleQuery = new SimpleQuery("*:*");

                // 设置分页起始记录数
                simpleQuery.setOffset((page - 1) * rows);
                // 设置页大小
                simpleQuery.setRows(rows);


                // 分页查询，得到分数分页对象
                ScoredPage<SolrItem> scoredPage = solrTemplate.queryForPage(simpleQuery, SolrItem.class);
                System.out.println("总记录数：" + scoredPage.getTotalElements());
                // 获取分页数据
                List<SolrItem> solrItems = scoredPage.getContent();

                Map<String, Object> data = new HashMap<>();
                data.put("rows", solrItems);
                // 总记录数
                data.put("total", scoredPage.getTotalElements());
                // 总页数
                data.put("totalPages", scoredPage.getTotalPages());
                return data;
            }


        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
