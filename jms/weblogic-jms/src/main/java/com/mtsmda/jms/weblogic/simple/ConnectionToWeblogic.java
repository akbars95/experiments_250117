package com.mtsmda.jms.weblogic.simple;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;

/**
 * Created by dminzat on 1/30/2017.
 */
public class ConnectionToWeblogic {

    public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
    public static final String JMS_FACTORY = "jms/TestConnectionFactory";
    public static final String QUEUE = "jms/TestQueue";

    protected QueueConnectionFactory queueConnectionFactory;
    protected QueueConnection queueConnection;
    protected QueueSession queueSession;
    protected QueueSender queueSender;
    protected QueueReceiver queueReceiver;
    protected Queue queue;
    protected TextMessage textMessage;

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
            if (null == queueSender) {
                queueReceiver.close();
            } else {
                queueSender.close();
            }
            queueSession.close();
            queueConnection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}