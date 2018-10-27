package com.mmmgdzl.search.mapper;

import com.mmmgdzl.common.pojo.SearchItem;

import java.util.List;

public interface ItemMapper {

    /**
     * 查询所有商品列表
     */
    List<SearchItem> getItemList();

}
