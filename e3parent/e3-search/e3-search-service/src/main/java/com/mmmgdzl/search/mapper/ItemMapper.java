package com.mmmgdzl.search.mapper;

import com.mmmgdzl.common.pojo.SearchItem;

import java.util.List;

public interface ItemMapper {

    /**
     * 查询所有商品列表
     */
    List<SearchItem> getItemList();

    /**
     * 根据商品id查询商品信息
     */
    SearchItem getItemById(Long itemId);
}
