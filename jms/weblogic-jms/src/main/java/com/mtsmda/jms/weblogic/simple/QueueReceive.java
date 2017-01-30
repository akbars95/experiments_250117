package com.mtsmda.jms.weblogic.simple;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

/**
 * Created by dminzat on 1/30/2017.
 */
public class QueueReceive extends ConnectionToWeblogic implements MessageListener {

    private boolean quit = false;

    public static void main(String[] args) {
        System.out.println("start");
        String url = null;//args[0];
        if (null == url) {
            url = "t3://localhost:7001";
        }
        InitialContext initialContext = getInitialContext(url);
        QueueReceive queueReceive = new QueueReceive();
        queueReceive.init(initialContext, QUEUE, queueReceive);

        synchronized (queueReceive) {
            while (!queueReceive.quit) {
                try {
                    queueReceive.wait();
                } catch (Exception e) {

                }
            }
        }
        queueReceive.close();
    }

    @Override
    public void onMessage(Message message) {
        try {
            String msg = null;
            if (message instanceof TextMessage) {
                msg = ((TextMessage) message).getText();
            } else {
                msg = message.toString();
            }
            System.out.println("Received = " + msg);
            if (msg.equalsIgnoreCase("quit")) {
                synchronized (this) {
                    quit = true;
                    this.notifyAll();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + " _ " + e);
        }
    }
}