package com.mmmgdzl.search.service.impl;

import com.mmmgdzl.common.pojo.SearchResult;
import com.mmmgdzl.search.dao.SearchDao;
import com.mmmgdzl.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品搜索service
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchDao searchDao;
    @Override
    public SearchResult search(String keyword, Integer page, Integer rows) throws Exception{
        //创建solrquery对象
        SolrQuery solrQuery = new SolrQuery();
        //设置查询条件
        solrQuery.setQuery(keyword);
        //设置分页条件
        if(page <= 0) {
            page = 1;
        }
        solrQuery.setStart((page-1) * rows);
        solrQuery.setRows(rows);
        //设置默认搜索域
        solrQuery.set("df", "item_title");
        //设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<em style='color:red'>");
        solrQuery.setHighlightSimplePost("</em>");
        //执行查询
        SearchResult searchResult = searchDao.search(solrQuery);
        //计算总页数
        searchResult.setTotalPages((int )Math.ceil(1.0 * searchResult.getRecordCount() / rows));
        return searchResult;
    }
}
