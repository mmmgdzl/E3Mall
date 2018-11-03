package com.mmmgdzl.search.listener;

import com.mmmgdzl.common.pojo.SearchItem;
import com.mmmgdzl.search.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 该监听器用于在添加商品时写入索引库
 */
public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        try {
            //从消息中取商品id
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            Long itemId = new Long(text);
            System.out.println(itemId+"*****************************");
            //等待添加商品事务提交
            Thread.sleep(100);
            //根据商品id查询商品信息
            SearchItem item = itemMapper.getItemById(itemId);
            //创建一个文档对象
            SolrInputDocument document = new SolrInputDocument();
            //向文档对象中添加域
            document.addField("id", item.getId());
            document.addField("item_title", item.getTitle());
            document.addField("item_sell_point", item.getSell_point());
            document.addField("item_price", item.getPrice());
            document.addField("item_image", item.getImage());
            document.addField("item_category_name", item.getCategory_name());
            //将文档对象写入索引库
            solrServer.add(document);
            //提交
            solrServer.commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
