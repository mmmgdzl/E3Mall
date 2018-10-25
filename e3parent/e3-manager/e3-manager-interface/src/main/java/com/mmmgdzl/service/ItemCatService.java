package com.mmmgdzl.service;

import com.mmmgdzl.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {

    /**
     *  查询商品分类
     */
    List<EasyUITreeNode> getItemCatList(Long parentId);

}
