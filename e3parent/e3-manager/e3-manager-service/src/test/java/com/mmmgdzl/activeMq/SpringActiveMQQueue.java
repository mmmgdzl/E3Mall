package com.mmmgdzl.activeMq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * 该类用于测试消息队列
 */
public class SpringActiveMQQueue {


    @Test
    public void fun1(){
        //初始化spring容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        //从容器中获取jmsTemplate对象
        JmsTemplate jmsTemplate = ac.getBean(JmsTemplate.class);
        //从容器中获取Destination对象
        Destination destination = (Destination) ac.getBean("queueDestination");
        //发送消息
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("send activemq message");
            }
        });
    }

}
