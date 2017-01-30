package com.mtsmda.jms.weblogic.simple;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * Created by dminzat on 1/30/2017.
 */
public class ConnectionToWeblogic {

    public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    public static final String JMS_FACTORY = "jms/TestConnectionFactory";
    public static final String QUEUE = "jms/TestQueue";
    public static final String TOPIC = "jms/TestTopic";

    protected QueueConnectionFactory queueConnectionFactory;
    protected QueueConnection queueConnection;
    protected QueueSession queueSession;
    protected QueueSender queueSender;
    protected QueueReceiver queueReceiver;
    protected Queue queue;
    protected TextMessage textMessage;

    protected TopicConnectionFactory topicConnectionFactory;
    protected TopicConnection topicConnection;
    protected TopicSession topicSession;
    protected TopicPublisher topicPublisher;
    protected TopicSubscriber topicSubscriber;
    protected Topic topic;

    protected boolean quit = false;

    public void init(Context context, String queueName, MessageListener messageListener) {
        try {
            queueConnectionFactory = (QueueConnectionFactory) context.lookup(JMS_FACTORY);
            queueConnection = queueConnectionFactory.createQueueConnection();
            queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queue = (Queue) context.lookup(queueName);
            if (null == messageListener) {
                queueSender = queueSession.createSender(queue);
                textMessage = queueSession.createTextMessage();
            } else {
                queueReceiver = queueSession.createReceiver(queue);
                queueReceiver.setMessageListener(messageListener);
            }
            queueConnection.start();
        } catch (NamingException | JMSException e) {
            System.out.println(e.getMessage() + " _ " + e);
        }
    }

    public void initTopic(Context context, String topicName, MessageListener messageListener) {
        try {
            topicConnectionFactory = (TopicConnectionFactory) context.lookup(JMS_FACTORY);
            topicConnection = topicConnectionFactory.createTopicConnection();
            topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = (Topic) context.lookup(topicName);
            if (null == messageListener) {
                topicPublisher = topicSession.createPublisher(topic);
                textMessage = topicSession.createTextMessage();
            } else {
                topicSubscriber = topicSession.createSubscriber(topic);
                topicSubscriber.setMessageListener(messageListener);
            }
            topicConnection.start();
        } catch (NamingException | JMSException e) {
            System.out.println(e.getMessage() + " _ " + e);
        }
    }

    protected static InitialContext getInitialContext(String url) {
        try {
            Hashtable hashtable = new Hashtable<>();
            hashtable.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
            hashtable.put(Context.PROVIDER_URL, url);
            return new InitialContext(hashtable);
        } catch (NamingException e) {
            System.out.println(e + " _ " + e.getMessage());
        }
        return null;
    }

    public void close() {
        try {
            if (!isNull(topicPublisher)) {
                topicPublisher.close();
            }
            if (!isNull(topicConnection)) {
                topicConnection.close();
            }
            if (!isNull(topicSession)) {
                topicSession.close();
            }

            if (!isNull(queueSender)) {
                queueReceiver.close();
            }
            if (!isNull(queueSender)) {
                queueSender.close();
            }
            if (!isNull(queueSession)) {
                queueSession.close();
            }
            if (!isNull(queueConnection)) {
                queueConnection.close();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private <T> boolean isNull(T object) {
        return null == object;
    }

}