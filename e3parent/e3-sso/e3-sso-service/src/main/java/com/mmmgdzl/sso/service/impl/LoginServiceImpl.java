package com.mmmgdzl.sso.service.impl;

import com.mmmgdzl.common.jedis.JedisClient;
import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.common.utils.JsonUtils;
import com.mmmgdzl.mapper.TbUserMapper;
import com.mmmgdzl.pojo.TbUser;
import com.mmmgdzl.pojo.TbUserExample;
import com.mmmgdzl.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TbUserMapper tbUserMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public E3Result userLogin(String username, String password) {
        //判断用户名与密码是否正确
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(list == null || list.size() == 0
                || !list.get(0).getPassword().equals(md5Password)) {
            return E3Result.build(400, "用户名或密码错误");
        }
        //生成Token
        String token = UUID.randomUUID().toString();
        //将用户信息保存于Redis
        TbUser tbUser = list.get(0);
        tbUser.setPassword(null);
        jedisClient.set("SESSION:" + token, JsonUtils.objectToJson(tbUser));
        //设置Session过期时间
        jedisClient.expire("SESSION" + token, SESSION_EXPIRE);
        return E3Result.ok(token);
    }
}
