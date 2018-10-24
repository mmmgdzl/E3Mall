package com.mmmgdzl.controller;

import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.content.service.ContentService;
import com.mmmgdzl.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/content/save")
    @ResponseBody
    public E3Result addContent(TbContent tbContent) {
        E3Result result = contentService.addContent(tbContent);

        return result;
    }
}
