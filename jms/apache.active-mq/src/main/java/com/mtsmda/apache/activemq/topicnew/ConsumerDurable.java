package com.mtsmda.apache.activemq.topicnew;

import com.mtsmda.apache.activemq.CommonConnectionToActiveMQNew;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;

/**
 * Created by dminzat on 2/3/2017.
 */
public class ConsumerDurable {

    public static void main(String[] args) {
        CommonConnectionToActiveMQNew commonConnectionToActiveMQNew = new CommonConnectionToActiveMQNew();
        commonConnectionToActiveMQNew.openSession("TopicNew", false, true, TopicConnection.class, "TopicConsumerDurable", true);
        commonConnectionToActiveMQNew.receiveTextMessage(message -> {
            try {
                System.out.println("TopicConsumerDurable = " + ((TextMessage)message).getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
   }

}