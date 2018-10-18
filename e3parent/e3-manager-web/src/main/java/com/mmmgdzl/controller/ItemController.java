package com.mmmgdzl.controller;

import com.mmmgdlz.common.pojo.EasyUIDataGridResult;
import com.mmmgdzl.pojo.TbItem;
import com.mmmgdzl.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public @ResponseBody TbItem getItemById(@PathVariable Long itemId) {

        return itemService.getItemById(itemId);
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
        //调用获取商品服务列表
        return itemService.getItemList(page, rows);
    }

}
