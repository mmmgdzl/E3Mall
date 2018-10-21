package com.mmmgdzl.service;

import com.mmmgdlz.common.pojo.EasyUIDataGridResult;
import com.mmmgdzl.pojo.TbItem;

public interface ItemService {
    /**
     * 通过id获取实体
     */
    public TbItem getItemById(Long id);

    /**
     * 获取easyui数据
     */
    EasyUIDataGridResult getItemList(Integer page, Integer rows);

}
