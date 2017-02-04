package com.mtsmda.apache.activemq.topicnew;

import com.mtsmda.apache.activemq.CommonConnectionToActiveMQNew;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;

/**
 * Created by dminzat on 2/3/2017.
 */
public class Consumer {

    public static void main(String[] args) {
        CommonConnectionToActiveMQNew commonConnectionToActiveMQNew = new CommonConnectionToActiveMQNew();
        commonConnectionToActiveMQNew.openSession("TopicNew", false, true, TopicConnection.class, "TopicConsumer", false);
        commonConnectionToActiveMQNew.receiveTextMessage(message -> {
            try {
                System.out.println("TopicConsumer1 = " + ((TextMessage)message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        CommonConnectionToActiveMQNew commonConnectionToActiveMQNew2 = new CommonConnectionToActiveMQNew();
        commonConnectionToActiveMQNew2.openSession("TopicNew", false, true, TopicConnection.class, "TopicConsumer2", false);
        commonConnectionToActiveMQNew2.receiveTextMessage(message -> {
            try {
                System.out.println("TopicConsumer2 = " + ((TextMessage)message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        CommonConnectionToActiveMQNew commonConnectionToActiveMQNew3 = new CommonConnectionToActiveMQNew();
        commonConnectionToActiveMQNew3.openSession("TopicNew", false, true, TopicConnection.class, "TopicConsumer3", false);
        commonConnectionToActiveMQNew3.receiveTextMessage(message -> {
            try {
                System.out.println("TopicConsumer3 = " + ((TextMessage)message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

}