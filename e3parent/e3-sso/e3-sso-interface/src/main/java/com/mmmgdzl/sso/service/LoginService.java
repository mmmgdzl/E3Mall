package com.mmmgdzl.sso.service;

import com.mmmgdzl.common.utils.E3Result;

public interface LoginService {

    /**
     * 执行登陆
     */
    E3Result userLogin(String username, String password);

}
