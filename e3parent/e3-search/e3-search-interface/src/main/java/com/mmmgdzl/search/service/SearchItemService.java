package com.mmmgdzl.search.service;

import com.mmmgdzl.common.utils.E3Result;

public interface SearchItemService {

    /**
     * 将数据库中的所有商品导入到索引库中
     */
    E3Result importAllItems();

}
