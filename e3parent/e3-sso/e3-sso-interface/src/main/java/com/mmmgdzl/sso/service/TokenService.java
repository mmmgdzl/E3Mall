package com.mmmgdzl.sso.service;

import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.pojo.TbUser;

public interface TokenService {

    /**
     * 根据Token查询用户信息
     */
    E3Result getUserByToken(String token);

}
