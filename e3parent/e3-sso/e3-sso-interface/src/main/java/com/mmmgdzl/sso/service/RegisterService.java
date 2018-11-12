package com.mmmgdzl.sso.service;

import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.pojo.TbUser;

/**
 * 用户注册处理Service
 */
public interface RegisterService {

    /**
     * 查询数据是否重复
     */
    E3Result checkData(String param, Integer type);

    /**
     * 注册新用户
     */
    E3Result register(TbUser tbUser);


}
