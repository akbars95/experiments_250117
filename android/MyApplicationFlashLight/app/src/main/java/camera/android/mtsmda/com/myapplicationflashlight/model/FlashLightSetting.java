package camera.android.mtsmda.com.myapplicationflashlight.model;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by dminzat on 8/28/2017.
 */

public class FlashLightSetting {

    private Integer flashLightId;
    /**
     * 23H10M15S
     */
    private String timerValue;

    private static final String NULL_HMS = "00";
    private static final String SPACE = " ";

    public static final String HOUR = "H";
    public static final String MINUTE = "M";
    public static final String SECOND = "S";

    public FlashLightSetting(String timerValue) {
        this.timerValue = timerValue;
    }

    public FlashLightSetting(Integer flashLightId, String timerValue) {
        this(timerValue);
        this.flashLightId = flashLightId;
    }

    public FlashLightSetting(Integer hours, Integer minutes, Integer seconds) {
        this(String.format("%02d%s%02d%s%02d%s", hours, HOUR, minutes, MINUTE, seconds, SECOND));
    }

    public Integer getFlashLightId() {
        return flashLightId;
    }

    public void setFlashLightId(Integer flashLightId) {
        this.flashLightId = flashLightId;
    }

    public String getTimerValue() {
        return timerValue;
    }

    public void setTimerValue(String timerValue) {
        this.timerValue = timerValue;
    }

    public long getSeconds() {
        return TimeUnit.HOURS.toSeconds(convertStringToLocal(getHour())) + TimeUnit.MINUTES.toSeconds(convertStringToLocal(getMinute())) + convertStringToLocal(getSecond());
    }

    public String getTimerName(String hourResource, String minuteResource, String secondResource) {
        StringBuilder result = new StringBuilder();
        if(StringUtils.isNotBlank(getHour()) && !getHour().equals(NULL_HMS)){
            result.append(getHour()).append(SPACE).append(hourResource).append(SPACE);
        }

        if(StringUtils.isNotBlank(getMinute()) && !getMinute().equals(NULL_HMS)){
            result.append(getMinute()).append(SPACE).append(minuteResource).append(SPACE);
        }

        if(StringUtils.isNotBlank(getSecond()) && !getSecond().equals(NULL_HMS)){
            result.append(getSecond()).append(SPACE).append(secondResource);
        }

        if(result.toString().endsWith(SPACE)){
            return result.toString().substring(0, result.toString().length() - SPACE.length());
        }
        return result.toString();
    }

    public String getHour() {
        return this.timerValue.substring(0, 2);
    }

    public String getMinute() {
        return this.timerValue.substring(3, 5);
    }

    public String getSecond() {
        return this.timerValue.substring(6, 8);
    }

    private Long convertStringToLocal(String in) {
        if (StringUtils.isNotBlank(in)) {
            return Long.valueOf(in);
        }
        throw new RuntimeException(in + " String is null or empty!");
    }

}