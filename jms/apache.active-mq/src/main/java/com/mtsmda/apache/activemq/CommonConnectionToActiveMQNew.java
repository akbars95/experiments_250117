package com.mtsmda.apache.activemq;

import com.mtsmda.jms.common.ExceptionHandler;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang3.StringUtils;

import javax.jms.*;
import java.time.LocalDateTime;

/**
 * Created by dminzat on 1/30/2017.
 */
public class CommonConnectionToActiveMQNew {

    private TopicConnectionFactory topicConnectionFactory;
    private QueueConnectionFactory queueConnectionFactory;
    private TopicConnection topicConnection;
    private QueueConnection queueConnection;
    private TopicSession topicSession;
    private QueueSession queueSession;
    private Topic topic;
    private Queue queue;
    private TopicPublisher topicPublisher;
    private QueueSender queueSender;
    private TopicSubscriber topicSubscriber;
    private QueueReceiver queueReceiver;

    private <T> T getConnection(boolean isTopic, Class<T> tClass, String clientName) {
        if (isTopic) {
            if (null == topicConnection) {
                try {
                    topicConnectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
                    topicConnection = topicConnectionFactory.createTopicConnection();
                    if (StringUtils.isNotBlank(clientName)) {
                        topicConnection.setClientID(clientName);
                    } else {
                        topicConnection.setClientID("Client_" + LocalDateTime.now());
                    }
                    topicConnection.start();
                } catch (Exception e) {
                    System.out.println(ExceptionHandler.toString(e));
                }
            }
            return (T) topicConnection;
        } else {
            if (null == queueConnection) {
                try {
                    queueConnectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_BROKER_URL);
                    queueConnection = queueConnectionFactory.createQueueConnection();
                    if (StringUtils.isNotBlank(clientName)) {
                        queueConnection.setClientID("Client_" + LocalDateTime.now());
                    } else {
                        queueConnection.setClientID(clientName);
                    }
                    queueConnection.start();
                } catch (Exception e) {
                    System.out.println(ExceptionHandler.toString(e));
                }
            }
            return (T) queueConnection;
        }
    }

    public <T extends Connection> Session openSession(String queueOrTopicName, boolean isProducer, boolean isTopic, Class<T> tClass, String clientName, boolean isDurable) {
        /*if (isProducer *//*|| is*//*)*/
            if (isTopic) {
                try {
                    topicSession = ((TopicConnection) getConnection(isTopic, tClass, clientName)).createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
                    topic = topicSession.createTopic(queueOrTopicName);

                    if (isProducer) {
                        topicPublisher = topicSession.createPublisher(topic);
                    } else {
                        if (isDurable) {
                            topicSubscriber = topicSession.createDurableSubscriber(topic, clientName);
                        } else {
                            topicSubscriber = topicSession.createSubscriber(topic);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(ExceptionHandler.toString(e));
                }
                return topicSession;
            } else {
                try {
                    queueSession = ((QueueConnection) getConnection(isTopic, tClass, clientName)).createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                    queue = queueSession.createQueue(queueOrTopicName);

                    if (isProducer) {
                        queueSender = queueSession.createSender(queue);
                    } else {
                        queueReceiver = queueSession.createReceiver(queue);
                    }
                } catch (Exception e) {
                    System.out.println(ExceptionHandler.toString(e));
                }
                return queueSession;
            }
    }



    public void sendTextMessage(String text) {
        try {
            if (null != queueSession) {
                TextMessage textMessage = queueSession.createTextMessage();
                textMessage.setText(text);
                queueSender.send(textMessage);
            } else {
                TextMessage textMessage = topicSession.createTextMessage();
                textMessage.setText(text);
                topicPublisher.send(textMessage);
            }
        } catch (Exception e) {
            System.out.println(ExceptionHandler.toString(e));
        }

    }

    public void receiveTextMessage() {
        try {
            if (null != queueSession) {
                Message receive = queueReceiver.receive();
                if (receive instanceof TextMessage) {
                    System.out.println("receiveTextMessage - " + ((TextMessage) receive).getText());
                } else {
                    throw new RuntimeException("This message not TextMessage type! Current is " + receive.getClass().getCanonicalName());
                }
            } else {
                Message receive = topicSubscriber.receive();
                if (receive instanceof TextMessage) {
                    System.out.println("receiveTextMessage - " + ((TextMessage) receive).getText());
                } else {
                    throw new RuntimeException("This message not TextMessage type! Current is " + receive.getClass().getCanonicalName());
                }
            }
        } catch (Exception e) {
            System.out.println(ExceptionHandler.toString(e));
        }
    }

    public void receiveTextMessage(MessageListener messageListener) {
        try {
            if (null != queueSession) {
                queueReceiver.setMessageListener(messageListener);
            } else {
                topicSubscriber.setMessageListener(messageListener);
            }
        } catch (Exception e) {
            System.out.println(ExceptionHandler.toString(e));
        }
    }

}