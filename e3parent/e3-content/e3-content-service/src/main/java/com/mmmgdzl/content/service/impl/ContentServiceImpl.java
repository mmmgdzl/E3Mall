package com.mmmgdzl.content.service.impl;

import com.mmmgdzl.common.jedis.JedisClient;
import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.common.utils.JsonUtils;
import com.mmmgdzl.content.service.ContentService;
import com.mmmgdzl.mapper.TbContentMapper;
import com.mmmgdzl.pojo.TbContent;
import com.mmmgdzl.pojo.TbContentExample;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${CONTENT_LIST}")
    private String CONTENT_LIST;

    @Override
    public E3Result addContent(TbContent tbContent) {
        //补全数据
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        //执行插入
        tbContentMapper.insert(tbContent);

        //缓存同步(删除缓存)
        jedisClient.hdel(CONTENT_LIST, tbContent.getCategoryId().toString());
        return E3Result.ok();
    }

    @Override
    public List<TbContent> getContentListByCid(Long cid) {
        try{
            //查询缓存中是否有数据
            String listJson = jedisClient.hget(CONTENT_LIST, cid + "");
            //若不为空则转为列表后返回
            if(StringUtils.isNotBlank(listJson)) {
                List<TbContent> contentList = JsonUtils.jsonToList(listJson, TbContent.class);
                return contentList;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        //设置查询条件
        criteria.andCategoryIdEqualTo(cid);
        //执行查询
        List<TbContent> contentList = tbContentMapper.selectByExampleWithBLOBs(tbContentExample);

        //将数据存入缓存
        try{
            jedisClient.hset(CONTENT_LIST, cid + "", JsonUtils.objectToJson(contentList));
        }catch (Exception e) {
            e.printStackTrace();
        }

        return contentList;
    }
}
