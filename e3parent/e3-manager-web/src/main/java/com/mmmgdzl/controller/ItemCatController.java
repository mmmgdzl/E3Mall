package com.mmmgdzl.controller;

import com.mmmgdlz.common.pojo.EasyUITreeNode;
import com.mmmgdzl.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类管理
 */
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCatList(@RequestParam(name="id", defaultValue = "0") Long parentId) {
        //调用函数取得节点列表
        List<EasyUITreeNode> list = itemCatService.getItemCatList(parentId);
        return list;
    }

}
