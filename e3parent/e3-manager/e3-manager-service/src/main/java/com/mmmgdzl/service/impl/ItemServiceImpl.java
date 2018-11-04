package com.mmmgdzl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmmgdzl.common.jedis.JedisClient;
import com.mmmgdzl.common.pojo.EasyUIDataGridResult;
import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.common.utils.IDUtils;
import com.mmmgdzl.common.utils.JsonUtils;
import com.mmmgdzl.mapper.TbItemDescMapper;
import com.mmmgdzl.mapper.TbItemMapper;
import com.mmmgdzl.pojo.TbItem;
import com.mmmgdzl.pojo.TbItemDesc;
import com.mmmgdzl.pojo.TbItemExample;
import com.mmmgdzl.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;
    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_ITEM_PRE}")
    private String REDIS_ITEM_PRE;
    @Value("${REDIS_ITEM_POST}")
    private String REDIS_ITEM_POST;
    @Value("${REDIS_ITEM_DESC_POST}")
    private String REDIS_ITEM_DESC_POST;
    @Value("${ITEM_CACHE_EXPIRE}")
    private Integer ITEM_CACHE_EXPIRE;

    @Override
    public TbItem getItemById(Long id) {
        //查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_PRE + ":" + id + ":" + REDIS_ITEM_POST);
            if(StringUtils.isNotBlank(json)) {
                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //若缓存中无此商品信息则查询数据库并放入缓存
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        if(tbItem != null) {
            try {
                //将商品信息放入缓存
                jedisClient.set(REDIS_ITEM_PRE + ":" + id + ":" + REDIS_ITEM_POST, JsonUtils.objectToJson(tbItem));
                //设置过期时间
                jedisClient.expire(REDIS_ITEM_PRE + ":" + id + ":" + REDIS_ITEM_POST, ITEM_CACHE_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tbItem;
    }

    @Override
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //设置分页
        PageHelper.startPage(page, rows);

        //执行查询
        TbItemExample tbItemExample = new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(tbItemExample);

        //创建返回值对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);

        //获取分页结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);

        //取总记录数
        result.setTotal(pageInfo.getTotal());
        
        return result;
    }

    @Override
    public E3Result addItem(TbItem item, String desc) {
        //生成商品id
        final Long id = IDUtils.genItemId();
        //补全item数据
        item.setId(id);
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //向商品表插入数据
        tbItemMapper.insert(item);
        //创建商品描述表pojo对象
        TbItemDesc tbItemDesc = new TbItemDesc();
        //补全商品描述包pojo对象数据
        tbItemDesc.setItemId(id);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setItemDesc(desc);
        //向商品描述表插入数据
        tbItemDescMapper.insert(tbItemDesc);
        //发送商品添加消息到消息队列
        jmsTemplate.send(topicDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(id + "");
            }
        });
        //返回成功信息
        return E3Result.ok();
    }

    @Override
    public TbItemDesc getItemDescById(Long id) {
        //查询缓存
        try {
            String json = jedisClient.get(REDIS_ITEM_PRE + ":" + id + ":" + REDIS_ITEM_DESC_POST);
            if(StringUtils.isNotBlank(json)) {
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return itemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //从数据库中获取数据
        TbItemDesc tbItemDesc = tbItemDescMapper.selectByPrimaryKey(id);

        if(tbItemDesc != null) {
            try {
                //将商品信息放入缓存
                jedisClient.set(REDIS_ITEM_PRE + ":" + id + ":" + REDIS_ITEM_DESC_POST, JsonUtils.objectToJson(tbItemDesc));
                //设置过期时间
                jedisClient.expire(REDIS_ITEM_PRE + ":" + id + ":" + REDIS_ITEM_DESC_POST, ITEM_CACHE_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tbItemDesc;
    }
}
