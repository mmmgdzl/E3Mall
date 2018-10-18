package com.mmmgdzl.pagehelper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mmmgdzl.mapper.TbItemMapper;
import com.mmmgdzl.pojo.TbItem;
import com.mmmgdzl.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class PageHelperTest {

    @Test
    public void fun1() throws Exception {
        //初始化spring容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        //从容器中获取mapper代理对象
        TbItemMapper tbm = ac.getBean(TbItemMapper.class);
        PageHelper.startPage(1,10);
        //执行查询
        TbItemExample tbe = new TbItemExample();
        List<TbItem> list = tbm.selectByExample(tbe);
        //取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        System.out.println("total:" + pageInfo.getTotal());
        System.out.println("currentPage:" + pageInfo.getPageNum());
        System.out.println("pagesize:" + pageInfo.getPageSize());
        System.out.println("pagenum:" + pageInfo.getPages());
        System.out.println("listsize:" + list.size());
    }
}
