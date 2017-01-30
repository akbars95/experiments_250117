package com.mtsmda.jms.weblogic.simple;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

/**
 * Created by dminzat on 1/30/2017.
 */
public class TopicReceive extends ConnectionToWeblogic implements MessageListener{

    public static void main(String[] args) {
        InitialContext initialContext = getInitialContext("t3://localhost:7001");
        TopicReceive topicReceive = new TopicReceive();
        topicReceive.initTopic(initialContext, TOPIC, topicReceive);
        synchronized (topicReceive){
            while (!topicReceive.quit){
                try{
                    topicReceive.wait();
                }
                catch (Exception e){

                }
            }
        }
        topicReceive.close();
    }

    @Override
    public void onMessage(Message message) {
        try {
           String msg;
            if(message instanceof TextMessage){
                msg = ((TextMessage) message).getText();
            }else{
                msg = message.toString();
            }
            System.out.println("JMS Message Receive " + msg);
            if(msg.equalsIgnoreCase("quit")){
                synchronized (this){
                    quit = true;
                    this.notifyAll();
                }
            }
        }
        catch (Exception e){

        }
    }
}