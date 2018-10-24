package com.mmmgdzl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmmgdlz.common.pojo.EasyUIDataGridResult;
import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.common.utils.IDUtils;
import com.mmmgdzl.mapper.TbItemDescMapper;
import com.mmmgdzl.mapper.TbItemMapper;
import com.mmmgdzl.pojo.TbItem;
import com.mmmgdzl.pojo.TbItemDesc;
import com.mmmgdzl.pojo.TbItemExample;
import com.mmmgdzl.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public TbItem getItemById(Long id) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
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
        Long id = IDUtils.genItemId();
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
        //返回成功信息
        return E3Result.ok();
    }
}
