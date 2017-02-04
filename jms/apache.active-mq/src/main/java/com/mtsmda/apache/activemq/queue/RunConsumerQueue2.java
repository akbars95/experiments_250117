package com.mtsmda.apache.activemq.queue;

import com.mtsmda.apache.activemq.CommonConnectionToActiveMQ;

/**
 * Created by dminzat on 1/31/2017.
 */
public class RunConsumerQueue2 {

    public static void main(String[] args) {
        CommonConnectionToActiveMQ commonConnectionToActiveMQ = new CommonConnectionToActiveMQ();
        commonConnectionToActiveMQ.openSession("FirstTextMessage", false, false, "Consumer2");
        /*while (true){
            commonConnectionToActiveMQ.receiveTextMessage();
        }*/
        commonConnectionToActiveMQ.receiveTextMessageWithListener();

        /*commonConnectionToActiveMQ.openSession("FirstBytesMessage", false);
        commonConnectionToActiveMQ.receiveBytesMessage();

        commonConnectionToActiveMQ.openSession("FirstObjectMessage", false);
        commonConnectionToActiveMQ.receiveObjectMessage();

        commonConnectionToActiveMQ.openSession("FirstStreamMessage", false);
        commonConnectionToActiveMQ.receiveStreamMessage();

        commonConnectionToActiveMQ.openSession("FirstMapMessage", false);
        commonConnectionToActiveMQ.receiveMapMessage();*/

    }

}