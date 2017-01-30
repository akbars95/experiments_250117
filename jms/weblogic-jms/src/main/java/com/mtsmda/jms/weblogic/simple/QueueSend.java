package com.mtsmda.jms.weblogic.simple;

import javax.jms.JMSException;
import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by dminzat on 1/27/2017.
 * <p>
 * JMS Server - TestJMSServer
 * JMS Module - TestJMSModule
 * SubDeployment - TestSubdeployment
 * Connection Factory - TestConnectionFactory - JNDI(jms/TestConnectionFactory)
 * JMS Queue - TestQueue - JNDI(jms/TestQueue)
 */
public class QueueSend extends ConnectionToWeblogic{

    private static void readAndSend(QueueSend queueSend) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            String line = null;
            boolean quitNow = false;
            do {
                System.out.print("Enter message(write 'quit' for exit):\n");
                line = bufferedReader.readLine();
                if (null != line && !line.trim().isEmpty()) {
                    queueSend.send(line);
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
            queueSender.send(textMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = null;//args[0];
        if (null == url) {
            url = "t3://localhost:7001";
        }
        InitialContext initialContext = getInitialContext(url);
        QueueSend queueSend = new QueueSend();
        queueSend.init(initialContext, QUEUE, null);
        readAndSend(queueSend);
        queueSend.close();
    }


}