package com.mmmgdzl.sso.controller;

import com.mmmgdzl.common.utils.CookieUtils;
import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 该类用于处理登陆请求
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;


    @RequestMapping("/page/login")
    public String showLogin() {
        return "login";
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public E3Result login(String username, String password,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        E3Result e3Result = loginService.userLogin(username, password);
        //判断是否登录成功
        //如果登陆成功
        if(e3Result.getStatus() == 200) {
            //获取Token
            String token = (String) e3Result.getData();
            //将Token放入Cookie
            CookieUtils.setCookie(request, response, TOKEN_KEY, token);
        }
        //返回E3Result
        return e3Result;
    }

}
