package com.mmmgdzl.search.dao;

import com.mmmgdzl.common.pojo.SearchItem;
import com.mmmgdzl.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 商品搜索DAO
 */
@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;


    /**
     * 根据查询条件查询索引
     */
    public SearchResult search(SolrQuery sq) throws Exception {
        //根据query查询索引库
        QueryResponse queryResponse = solrServer.query(sq);
        //取查询结果
        SolrDocumentList documentList = queryResponse.getResults();
        //取查询结果总记录数
        Long numFound = documentList.getNumFound();
        SearchResult searchResult = new SearchResult();
        searchResult.setRecordCount(numFound);
        //取商品列表 , 需要取高亮显示
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        List<SearchItem> list = new LinkedList<>();
        for(SolrDocument document : documentList) {
            SearchItem searchItem = new SearchItem();
            searchItem.setId((String)document.get("id"));
            searchItem.setCategory_name((String)document.get("item_category_name"));
            searchItem.setImage((String) document.get("item_image"));
            searchItem.setPrice((Long) document.get("item_price"));
            searchItem.setSell_point((String) document.get("item_sell_point"));
            //取高亮显示
            List<String> itemTitle = highlighting.get(document.get("id")).get("item_title");
            if(itemTitle != null && itemTitle.size() != 0) {
                searchItem.setTitle(itemTitle.get(0));
            } else {
                searchItem.setTitle((String) document.get("item_title"));
            }
            list.add(searchItem);
        }
        searchResult.setItemList(list);

        return searchResult;
    }
}
