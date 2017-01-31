package com.mtsmda.apache.activemq;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dminzat on 1/31/2017.
 */
public class RunConsumer {

    public static void main(String[] args) {
        CommonConnectionToActiveMQ commonConnectionToActiveMQ = new CommonConnectionToActiveMQ();
        commonConnectionToActiveMQ.getSession("FirstTextMessage", false);
        commonConnectionToActiveMQ.receiveTextMessage();

        commonConnectionToActiveMQ.getSession("FirstBytesMessage", false);
        commonConnectionToActiveMQ.receiveBytesMessage();

        commonConnectionToActiveMQ.getSession("FirstObjectMessage", false);
        commonConnectionToActiveMQ.receiveObjectMessage();

        commonConnectionToActiveMQ.getSession("FirstStreamMessage", false);
        commonConnectionToActiveMQ.receiveStreamMessage();

        commonConnectionToActiveMQ.getSession("FirstMapMessage", false);
        commonConnectionToActiveMQ.receiveMapMessage();

    }

}