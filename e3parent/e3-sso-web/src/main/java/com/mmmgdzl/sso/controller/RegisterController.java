package com.mmmgdzl.sso.controller;

import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.pojo.TbUser;
import com.mmmgdzl.sso.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 注册功能controller
 */
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/page/register")
    public String showRegister() {
        return "register";
    }

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public E3Result checkData(@PathVariable String param, @PathVariable Integer type) {
        return registerService.checkData(param, type);
    }

    @RequestMapping("/user/register")
    @ResponseBody
    public E3Result register(TbUser tbUser) {
        return registerService.register(tbUser);
    }

}
