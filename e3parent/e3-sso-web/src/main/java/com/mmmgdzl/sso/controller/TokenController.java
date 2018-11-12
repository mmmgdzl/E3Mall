package com.mmmgdzl.sso.controller;

import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.common.utils.JsonUtils;
import com.mmmgdzl.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;

@Controller
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value = "/user/token/{token}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    private String getToken(@PathVariable String token, String callback) {
        E3Result e3Result = tokenService.getUserByToken(token);
        String result = JsonUtils.objectToJson(e3Result);
        //如果callback不为空,则转为jsonp响应格式
        if(StringUtils.isNotBlank(callback)) {
            result = callback + "(" + result + ")";
        }
        return result;
    }

}
