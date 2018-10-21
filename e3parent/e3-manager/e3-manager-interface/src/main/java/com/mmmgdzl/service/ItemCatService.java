package com.mmmgdzl.service;

import com.mmmgdlz.common.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {

    /**
     *  查询商品分类
     */
    List<EasyUITreeNode> getItemCatList(Long parentId);

}
