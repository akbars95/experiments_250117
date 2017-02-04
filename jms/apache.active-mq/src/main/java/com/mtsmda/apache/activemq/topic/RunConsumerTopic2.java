package com.mtsmda.apache.activemq.topic;

import com.mtsmda.apache.activemq.CommonConnectionToActiveMQ;

/**
 * Created by dminzat on 1/31/2017.
 */
public class RunConsumerTopic2 {

    public static void main(String[] args) {
        CommonConnectionToActiveMQ commonConnectionToActiveMQ = new CommonConnectionToActiveMQ();
        commonConnectionToActiveMQ.openSession("FirstTextMessageTopic", false, true, "Cons1");
        commonConnectionToActiveMQ.receiveTextMessageWithListener();

    }

}