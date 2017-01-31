package com.mtsmda.apache.activemq;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dminzat on 1/31/2017.
 */
public class RunProducer {

    public static void main(String[] args) {
        CommonConnectionToActiveMQ commonConnectionToActiveMQ = new CommonConnectionToActiveMQ();
        commonConnectionToActiveMQ.getSession("FirstTextMessage", true);
        commonConnectionToActiveMQ.sendTextMessage("Hello, my friend!");

        FootballClub footballClub = new FootballClub(19, "Barcelona", "Spain", "Barcelona");

        commonConnectionToActiveMQ.getSession("FirstBytesMessage", true);
        commonConnectionToActiveMQ.sendBytesMessage(footballClub);

        commonConnectionToActiveMQ.getSession("FirstObjectMessage", true);
        commonConnectionToActiveMQ.sendObjectMessage(footballClub);

        commonConnectionToActiveMQ.getSession("FirstStreamMessage", true);
        commonConnectionToActiveMQ.sendStreamMessage(footballClub);

        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("footballClubId", footballClub.getFootballClubId());
        stringObjectMap.put("name", footballClub.getName());
        stringObjectMap.put("countryName", footballClub.getCountryName());
        stringObjectMap.put("cityName", footballClub.getCityName());

        commonConnectionToActiveMQ.getSession("FirstMapMessage", true);
        commonConnectionToActiveMQ.sendMapMessage(stringObjectMap);

    }

}