package com.mtsmda.apache.activemq.topic;

import com.mtsmda.apache.activemq.CommonConnectionToActiveMQ;
import com.mtsmda.jms.common.ExceptionHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

/**
 * Created by dminzat on 1/31/2017.
 */
public class RunProducerTopic {

    public static void main(String[] args) {
        CommonConnectionToActiveMQ commonConnectionToActiveMQ = new CommonConnectionToActiveMQ();
        commonConnectionToActiveMQ.openSession("FirstTextMessageTopic", true, true);

        System.out.print("Enter message(write 'quit' or shortcut 'q' for exit):\n");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            boolean quitNow = false;
            String line = null;
            do {
                line = bufferedReader.readLine();
                if (null != line && !line.trim().isEmpty()) {
                    quitNow = line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("q");
                }
                if(!quitNow){
                    line += " ldt - " + LocalDateTime.now();
                    System.out.println("Send - "+ line);
                    commonConnectionToActiveMQ.sendTextMessage(line);
                }else{
                    System.out.println("SEE YOU SOON!");
                }
            }
            while (!quitNow);
        } catch (Exception e) {
            ExceptionHandler.toString(e);
        }   }

}