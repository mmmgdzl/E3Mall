package com.mmmgdzl.service.impl;

import com.mmmgdzl.mapper.TbItemMapper;
import com.mmmgdzl.pojo.TbItem;
import com.mmmgdzl.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public TbItem getItemById(Long id) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(id);
        return tbItem;
    }
}
