package com.mmmgdzl.search.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 测试用
 */
public class SolrMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            //取出消息内容
            String text = ((TextMessage) message).getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
