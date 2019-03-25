package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.util.*;

/**
 * 商品服务接口实现类
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-04<p>
 */
@Service(interfaceName = "com.pinyougou.service.GoodsService")
@Transactional(rollbackFor = RuntimeException.class)
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SellerMapper sellerMapper;


    // sqlSession(开启事务)
    @Override
    public void save(Goods goods) {
        try {
            // 1. 往tb_goods表插入数据
            // 设置商品的审核状态
            goods.setAuditStatus("0"); // 未审核
            goodsMapper.insertSelective(goods);

            // 2. 往tb_goods_desc表插入数据
            // 把主表的主键id 设置给商品描述表的主键id
            goods.getGoodsDesc().setGoodsId(goods.getId());
            goodsDescMapper.insertSelective(goods.getGoodsDesc());


            // 3. 往tb_item表插入数据
            // 判断是否启用规格
            if ("1".equals(goods.getIsEnableSpec())) { // 启用规格

                for (Item item : goods.getItems()) {
                    // item : {spec : {}, price : 0, num : 9999, status : '0', isDefault : '0'}
                    // 设置SKU商品的标题 SPU名称 + 规格 {"网络":"联通4G","机身内存":"32G"}
                    StringBuilder title = new StringBuilder(goods.getGoodsName());
                    // 把规格 {"网络":"联通4G","机身内存":"32G"}转化成Map集合
                    Map<String, String> map = JSON.parseObject(item.getSpec(), Map.class);
                    for (String value : map.values()) {
                        title.append(" " + value);
                    }
                    item.setTitle(title.toString());

                    /** 设置SKU商品其它属性 */
                    setItemInfo(goods, item);

                    // 保存数据
                    itemMapper.insertSelective(item);
                }
            }else { // 不启用规格

                // {spec : {}, price : 0, num : 9999, status : '0', isDefault : '0'}
                /** 创建SKU具体商品对象 */
                Item item = new Item();
                /** 设置SKU商品的标题 */
                item.setTitle(goods.getGoodsName());
                /** 设置SKU商品的价格 */
                item.setPrice(goods.getPrice());
                /** 设置SKU商品库存数据 */
                item.setNum(9999);
                /** 设置SKU商品启用状态 */
                item.setStatus("1");
                /** 设置是否默认*/
                item.setIsDefault("1");
                /** 设置规格选项 */
                item.setSpec("{}");
                /** 设置SKU商品其它属性 */
                setItemInfo(goods, item);

                // 保存数据
                itemMapper.insertSelective(item);
            }
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }

    }
    // 调用实现类中的方法
    // 如果出现了运行时异常就会回滚 否则就提交事务
    // sqlSession

    /** 设置SKU商品的其它信息 */
    private void setItemInfo(Goods goods, Item item){
        /**
         * goods.getGoodsDesc().getItemImages();
         * [{"color":"金色","url":"http://image.pinyougou.com/jd/wKgMg1qtKEOATL9nAAFti6upbx4132.jpg"},
         * {"color":"深空灰色","url":"http://image.pinyougou.com/jd/wKgMg1qtKHmAFxj7AAFZsBqChgk725.jpg"},
         * {"color":"银色","url":"http://image.pinyougou.com/jd/wKgMg1qtKJyAHQ9sAAFuOBobu-A759.jpg"}]
         */
        String itemImages = goods.getGoodsDesc().getItemImages();
        List<Map> imageList = JSON.parseArray(itemImages, Map.class);
        if (imageList != null && imageList.size() > 0) {
            // 设置SKU商品的图片
            item.setImage(imageList.get(0).get("url").toString());
        }

        // 设置SKU商品的三级分类id
        item.setCategoryid(goods.getCategory3Id());
        // 设置SKU商品的创建时间
        item.setCreateTime(new Date());
        // 设置SKU商品的修改时间
        item.setUpdateTime(item.getCreateTime());
        // 设置SKU商品的SPU的id
        item.setGoodsId(goods.getId());
        // 设置SKU商品的商家的id
        item.setSellerId(goods.getSellerId());

        // 搜索字段
        // 设置SKU商品的三级分类名称
        ItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id());
        item.setCategory(itemCat != null ? itemCat.getName() : "");

        // 设置SKU商品的品牌名称
        Brand brand = brandMapper.selectByPrimaryKey(goods.getBrandId());
        item.setBrand(brand != null ? brand.getName() : "");

        // 设置SKU商品的店铺名称
        Seller seller = sellerMapper.selectByPrimaryKey(goods.getSellerId());
        item.setSeller(seller != null ? seller.getNickName() : "");
    }

    @Override
    public void update(Goods goods) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public void deleteAll(Serializable[] ids) {

    }

    @Override
    public Goods findOne(Serializable id) {
        return null;
    }

    @Override
    public List<Goods> findAll() {
        return null;
    }

    @Override
    public PageResult findByPage(Goods goods, int page, int rows) {
        try{
            //  开启分页
            PageInfo<Map<String, Object>> pageInfo = PageHelper.startPage(page, rows)
                    .doSelectPageInfo(new ISelect() {
                @Override
                public void doSelect() {
                    goodsMapper.findAll(goods);
                }
            });
            List<Map<String, Object>> data = pageInfo.getList();
            // 迭代数据
            for (Map<String, Object> map : data) {
                // 获取三级分类id
                Long category3Id = (Long)map.get("category3Id");
                /** 判断三级分类id */
                if (category3Id != null){
                    // 查询一级分类的名称
                    String category1Name = itemCatMapper.selectByPrimaryKey(map.get("category1Id")).getName();
                    map.put("category1Name", category1Name);

                    // 查询二级分类的名称
                    String category2Name = itemCatMapper.selectByPrimaryKey(map.get("category2Id")).getName();
                    map.put("category2Name", category2Name);

                    // 查询三级分类的名称
                    String category3Name = itemCatMapper.selectByPrimaryKey(map.get("category3Id")).getName();
                    map.put("category3Name", category3Name);
                }
            }
            return new PageResult(pageInfo.getTotal(), data);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /** 修改商品的状态码 */
    public void updateStatus(String columnName, Long[] ids, String status){
        try{
            goodsMapper.updateStatus(columnName, ids, status);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /** 查询商品数据 */
    public Map<String,Object> getGoods(Long goodsId){
        try{

            Map<String,Object> dataModel = new HashMap<>();

            // 1. 查询tb_goods表
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);

            // 2. 查询tb_goods_desc表
            GoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);

            // 3. 查询tb_item表
            // SELECT * FROM `tb_item` WHERE goods_id=149187842867973 ORDER BY is_default DESC
            Example example = new Example(Item.class);
            // 创建条件对象
            Example.Criteria criteria = example.createCriteria();
            // goods_id=149187842867973
            criteria.andEqualTo("goodsId", goodsId);
            // ORDER BY is_default DESC (把默认的SKU排在最前面)
            example.orderBy("isDefault").desc();
            // 条件查询 (把itemList作为json数组来用)
            List<Item> itemList = itemMapper.selectByExample(example);


            dataModel.put("goods", goods);
            dataModel.put("goodsDesc", goodsDesc);
            dataModel.put("itemList", JSON.toJSONString(itemList));

            // 4. 查询商品的分类名称
            if (goods.getCategory3Id() != null){
                // 查询一级分类的名称
                String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
                dataModel.put("itemCat1", itemCat1);
                // 查询二级分类的名称
                String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
                dataModel.put("itemCat2", itemCat2);
                // 查询三级分类的名称
                String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();
                dataModel.put("itemCat3", itemCat3);
            }
            return dataModel;

        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    /** 根据多个goodsId查询SKU数据 */
    public List<Item> findItemByGoodsId(Long[] goodsIds){
        try{
            // SELECT * FROM `tb_item` WHERE STATUS = 1 AND goods_id IN (?,?,?)
            // 创建Example
            Example example = new Example(Item.class);
            // 创建条件对象
            Example.Criteria criteria = example.createCriteria();
            // STATUS = 1
            criteria.andEqualTo("status", 1);
            //  goods_id IN (?,?,?)
            criteria.andIn("goodsId", Arrays.asList(goodsIds));
            // 条件查询
            return itemMapper.selectByExample(example);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
