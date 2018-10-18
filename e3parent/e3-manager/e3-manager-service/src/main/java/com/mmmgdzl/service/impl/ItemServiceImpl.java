package com.mmmgdzl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmmgdlz.common.pojo.EasyUIDataGridResult;
import com.mmmgdzl.mapper.TbItemMapper;
import com.mmmgdzl.pojo.TbItem;
import com.mmmgdzl.pojo.TbItemExample;
import com.mmmgdzl.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

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


}
