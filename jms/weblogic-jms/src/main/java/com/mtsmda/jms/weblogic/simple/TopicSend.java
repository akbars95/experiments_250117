package com.mtsmda.jms.weblogic.simple;

import javax.jms.JMSException;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by dminzat on 1/30/2017.
 */
public class TopicSend extends ConnectionToWeblogic {

    private static void readAndSend(TopicSend topicSend) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            String line = null;
            boolean quitNow = false;
            do {
                System.out.print("Enter message(write 'quit' for exit):\n");
                line = bufferedReader.readLine();
                if (null != line && !line.trim().isEmpty()) {
                    topicSend.send(line);
                    System.out.println("JMS message Sent: " + line + "\n");
                    quitNow = line.equalsIgnoreCase("quit");
                }
            }
            while (!quitNow);
        } catch (Exception e) {
            System.out.println(e + " _ " + e.getMessage());
        }
    }

    public void send(String message) {
        try {
            textMessage.setText(message);
            topicPublisher.send(textMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        InitialContext initialContext = getInitialContext("t3://localhost:7001");
        TopicSend topicSend = new TopicSend();
        topicSend.initTopic(initialContext, TOPIC, null);
        readAndSend(topicSend);
        topicSend.close();
    }

}