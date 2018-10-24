package com.mmmgdzl.content.service.impl;

import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.content.service.ContentService;
import com.mmmgdzl.mapper.TbContentMapper;
import com.mmmgdzl.pojo.TbContent;
import com.mmmgdzl.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public E3Result addContent(TbContent tbContent) {
        //补全数据
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        //执行插入
        tbContentMapper.insert(tbContent);
        return E3Result.ok();
    }

    @Override
    public List<TbContent> getContentListByCid(Long cid) {
        TbContentExample tbContentExample = new TbContentExample();
        TbContentExample.Criteria criteria = tbContentExample.createCriteria();
        //设置查询条件
        criteria.andCategoryIdEqualTo(cid);
        //执行查询
        List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(tbContentExample);
        return list;
    }
}
