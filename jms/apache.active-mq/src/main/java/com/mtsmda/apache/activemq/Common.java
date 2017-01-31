package com.mtsmda.apache.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by dminzat on 1/30/2017.
 */
public class Common {

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageProducer messageProducer;

    public void send(){
        try {
            connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("SAMPLE");
            messageProducer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("sample message");
            messageProducer.send(textMessage);
        }
        catch (Exception e){

        }
    }

    public static void main(String[] args) {
        new Common().send();
    }

}