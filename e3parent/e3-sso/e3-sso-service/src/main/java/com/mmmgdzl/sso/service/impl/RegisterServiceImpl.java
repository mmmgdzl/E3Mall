package com.mmmgdzl.sso.service.impl;

import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.mapper.TbUserMapper;
import com.mmmgdzl.pojo.TbUser;
import com.mmmgdzl.pojo.TbUserExample;
import com.mmmgdzl.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper tbUserMapper;
    
    @Override
    public E3Result checkData(String param, Integer type) {
        //根据不同的查询条件执行查询
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        //1:用户名 2:手机号 3:邮箱
        if(type == 1) {
            criteria.andUsernameEqualTo(param);
        } else if(type == 2) {
            criteria.andPhoneEqualTo(param);
        } else if(type == 3) {
            criteria.andEmailEqualTo(param);
        } else {
            return E3Result.build(400, "数据类型错误");
        }

        //执行查询
        List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);
        //如果有查询结果数据返回false，否则返回true
        if(list != null && list.size() > 0) {
            return E3Result.ok(false);
        }
        return E3Result.ok(true);
    }

    @Override
    public E3Result register(TbUser tbUser) {
        //校验数据有效性
        if(StringUtils.isBlank(tbUser.getUsername()) || StringUtils.isBlank(tbUser.getPassword())
                || StringUtils.isBlank(tbUser.getPhone())) {
            return E3Result.build(400, "用户数据不完整");
        }
        //判断数据是否重复(用户名，手机号)
        E3Result result1 = checkData(tbUser.getUsername(), 1);
        if(!(boolean) result1.getData()) {
            return E3Result.build(400, "此用户名已经被使用!");
        }
        E3Result result2 = checkData(tbUser.getPhone(), 2);
        if(!(boolean) result2.getData()) {
            return E3Result.build(400, "此手机号已经注册!");
        }
        //将密码使用MD5加密
        String password = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(password);
        //补全创建日期
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        //将用户插入表中
        tbUserMapper.insert(tbUser);
        return E3Result.ok();
    }
}
