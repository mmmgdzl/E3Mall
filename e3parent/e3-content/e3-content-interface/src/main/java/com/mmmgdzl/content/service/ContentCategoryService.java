package com.mmmgdzl.content.service;

import com.mmmgdzl.common.pojo.EasyUITreeNode;
import com.mmmgdzl.common.utils.E3Result;

import java.util.List;

public interface ContentCategoryService {

    /**
     * 根据父节点查询节点
     */
    List<EasyUITreeNode> getContentCatList(Long parentId);

    /**
     * 添加新节点
     */
    E3Result addNode(Long parentId, String name);

    /**
     * 修改节点名称
     */
    E3Result update(Long id, String name);

    /**
     * 删除节点
     */
    E3Result delete(Long id);

}
