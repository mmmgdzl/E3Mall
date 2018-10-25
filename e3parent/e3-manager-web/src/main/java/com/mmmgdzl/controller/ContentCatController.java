package com.mmmgdzl.controller;

import com.mmmgdzl.common.pojo.EasyUITreeNode;
import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ContentCatController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(name = "id", defaultValue = "0") Long parentId) {
        List<EasyUITreeNode> resultList = contentCategoryService.getContentCatList(parentId);
        return resultList;
    }

    /**
     * 新增节点
     */
    @RequestMapping("/content/category/create")
    @ResponseBody
    public E3Result addNode(Long parentId, String name) {
        E3Result result = contentCategoryService.addNode(parentId, name);
        return result;
    }

    /**
     * 修改节点
     */
    @RequestMapping("/content/category/update")
    @ResponseBody
    public E3Result updateNode(Long id, String name) {
        E3Result result = contentCategoryService.update(id, name);
        return result;
    }

    /**
     * 删除节点
     */
    @RequestMapping("/content/category/delete")
    @ResponseBody
    public E3Result deleteNode(Long id) {
        E3Result result = contentCategoryService.delete(id);
        return  result;
    }

}
