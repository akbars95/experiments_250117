package com.mtsmda.apache.activemq.topicnew;

import com.mtsmda.apache.activemq.CommonConnectionToActiveMQNew;
import com.mtsmda.jms.common.ExceptionHandler;

import javax.jms.TopicConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

/**
 * Created by dminzat on 2/3/2017.
 */
public class Producer {

    public static void main(String[] args) {
        CommonConnectionToActiveMQNew commonConnectionToActiveMQNew = new CommonConnectionToActiveMQNew();
        commonConnectionToActiveMQNew.openSession("TopicNew", true, true, TopicConnection.class, "TopicProducer", false);

        System.out.print("Enter message(write 'quit' or shortcut 'q' for exit):\n");
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            boolean quitNow = false;
            String line = null;
            do {
                line = bufferedReader.readLine();
                if (null != line && !line.trim().isEmpty()) {
                    quitNow = line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("q");
                }
                if (!quitNow) {
                    line += " ldt - " + LocalDateTime.now();
                    System.out.println("Send - " + line);
                    commonConnectionToActiveMQNew.sendTextMessage(line);
                } else {
                    System.out.println("SEE YOU SOON!");
                }
            }
            while (!quitNow);
        } catch (Exception e) {
            ExceptionHandler.toString(e);
        }
    }

}