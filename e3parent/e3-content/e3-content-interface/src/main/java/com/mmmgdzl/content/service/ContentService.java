package com.mmmgdzl.content.service;


import com.mmmgdzl.common.utils.E3Result;
import com.mmmgdzl.pojo.TbContent;

import java.util.List;

public interface ContentService {

    /**
     * 添加一条新的数据
     */
    E3Result addContent(TbContent tbContent);

    /**
     * 通过cid查询列表
     */
    List<TbContent> getContentListByCid(Long cid);

}
