package com.mmmgdzl.content.service.impl;

import com.mmmgdzl.common.pojo.EasyUITreeNode;
import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.content.service.ContentCategoryService;
import com.mmmgdzl.mapper.TbContentCategoryMapper;
import com.mmmgdzl.pojo.TbContentCategory;
import com.mmmgdzl.pojo.TbContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        //根据parentId查询子节点列表
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
        //设置查询条件
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> catList = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        //转化为EasyUITreeNodeList
        List<EasyUITreeNode> result = new LinkedList<>();
        for(TbContentCategory tbContentCategory : catList) {
            EasyUITreeNode easyUITreeNode = new EasyUITreeNode();
            easyUITreeNode.setId(tbContentCategory.getId());
            easyUITreeNode.setText(tbContentCategory.getName());
            easyUITreeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            result.add(easyUITreeNode);
        }
        return result;
    }

    @Override
    public E3Result addNode(Long parentId, String name) {
        //创建新的节点对象
        TbContentCategory tbContentCategory = new TbContentCategory();
        //补全属性
        tbContentCategory.setName(name);
        tbContentCategory.setParentId(parentId);
        //新添加的节点一定是叶子节点
        tbContentCategory.setIsParent(false);
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);
        tbContentCategory.setCreated(new Date());
        tbContentCategory.setUpdated(new Date());
        //插入表中
        tbContentCategoryMapper.insert(tbContentCategory);

        //判断父节点的isParent属性是否为true, 若不是则改为true
        TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        if(!parent.getIsParent()) {
            //修改父节点的isParent属性为1
            parent.setIsParent(true);
            tbContentCategoryMapper.updateByPrimaryKey(parent);
        }
        //创建E3Result对象
        return E3Result.ok(tbContentCategory);
    }

    @Override
    public E3Result update(Long id, String name) {
        //创建tb_content_category表对应的pojo对象
        TbContentCategory tbContentCategory = new TbContentCategory();
        //补全修改属性
        tbContentCategory.setId(id);
        tbContentCategory.setName(name);
        //执行修改
        tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);

        return E3Result.ok();
    }

    @Override
    public E3Result delete(Long id) {
        //获取id对应的节点
        TbContentCategory tbContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        Long parentId = tbContentCategory.getParentId();
        //判断是否是父节点
        if(tbContentCategory.getIsParent()) {
            return E3Result.build(499, "无法删除有子节点的分类");
        } else {
            //获取父节点
            TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
            //执行删除
            tbContentCategoryMapper.deleteByPrimaryKey(id);
            //判断父节点是否为叶子节点
            TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = tbContentCategoryExample.createCriteria();
            criteria.andParentIdEqualTo(parentId);
            int count = tbContentCategoryMapper.countByExample(tbContentCategoryExample);
            if(count == 0) {
                parent.setIsParent(false);
                tbContentCategoryMapper.updateByPrimaryKey(parent);
            }
            return E3Result.ok();
        }
    }
}
