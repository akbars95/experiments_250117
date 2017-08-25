package camera.android.mtsmda.com.myapplicationflashlight.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dminzat on 8/25/2017.
 */

public class TimerName {

    private String name;
    private Long seconds;

    public TimerName(String name, Long seconds) {
        this.name = name;
        this.seconds = seconds;
    }

    public String getName() {
        return name;
    }

    public Long getSeconds() {
        return seconds;
    }

    public static List<String> getStringNames(List<TimerName> timerNames) {
        List<String> strings = new ArrayList<>();
//        timerNames.forEach(timerName -> strings.add(timerName.getName()));
        for (TimerName timerName : timerNames) {
            strings.add(timerName.getName());
        }
        return strings;
    }

}