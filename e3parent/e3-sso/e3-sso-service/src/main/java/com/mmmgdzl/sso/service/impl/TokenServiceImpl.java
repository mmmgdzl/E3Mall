package com.mmmgdzl.sso.service.impl;

import com.mmmgdzl.common.jedis.JedisClient;
import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.common.utils.JsonUtils;
import com.mmmgdzl.mapper.TbUserMapper;
import com.mmmgdzl.pojo.TbUser;
import com.mmmgdzl.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public E3Result getUserByToken(String token) {
        //根据token获取TbUser对象
        String json = jedisClient.get("SESSION:" + token);
        if(StringUtils.isBlank(json)) {
            //取不到用户信息
            return E3Result.build(201, "用户登录已经过期!");
        }
        //更新用户信息过期时间
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        //将json转为TbUser对象
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
        //返回结果
        return new E3Result(tbUser);
    }
}
