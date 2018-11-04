package com.mmmgdzl.service;

import com.mmmgdzl.common.pojo.EasyUIDataGridResult;
import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.pojo.TbItem;
import com.mmmgdzl.pojo.TbItemDesc;

public interface ItemService {
    /**
     * 通过id获取实体
     */
    public TbItem getItemById(Long id);

    /**
     * 根据id获取商品描述
     */
    public TbItemDesc getItemDescById(Long id);

    /**
     * 获取easyui数据
     */
    EasyUIDataGridResult getItemList(Integer page, Integer rows);

    /**
     * 添加新的商品信息
     */
    E3Result addItem(TbItem item, String desc);
}
