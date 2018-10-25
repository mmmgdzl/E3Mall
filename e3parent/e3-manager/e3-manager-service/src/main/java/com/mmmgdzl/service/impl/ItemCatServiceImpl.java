package com.mmmgdzl.service.impl;

import com.mmmgdzl.common.pojo.EasyUITreeNode;
import com.mmmgdzl.mapper.TbItemCatMapper;
import com.mmmgdzl.pojo.TbItemCat;
import com.mmmgdzl.pojo.TbItemCatExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ItemCatServiceImpl implements com.mmmgdzl.service.ItemCatService {

    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(Long parentId) {
        //根据parentid查询子节点列表
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria cirteria = tbItemCatExample.createCriteria();
        cirteria.andParentIdEqualTo(parentId);
        //执行查询并返回list
        List<TbItemCat> list = tbItemCatMapper.selectByExample(tbItemCatExample);
        //将list对象转化为EasyUITreeNodeList
        List<EasyUITreeNode> resultList = new LinkedList<>();
        for(TbItemCat tbItemCat : list) {
            //新建子节点对象
            EasyUITreeNode treeNode = new EasyUITreeNode();
            //设置属性
            treeNode.setId(tbItemCat.getId());
            treeNode.setText(tbItemCat.getName());
            treeNode.setState(tbItemCat.getIsParent()?"closed":"open");
            //将子节点添加至列表
            resultList.add(treeNode);
        }
        //将子节点列表返回
        return resultList;
    }
}
