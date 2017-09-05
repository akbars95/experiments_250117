package camera.android.mtsmda.com.myapplicationflashlight;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import camera.android.mtsmda.com.myapplicationflashlight.model.FlashLightSetting;

import static camera.android.mtsmda.com.myapplicationflashlight.model.FlashLightSetting.HOUR;
import static camera.android.mtsmda.com.myapplicationflashlight.model.FlashLightSetting.MINUTE;
import static camera.android.mtsmda.com.myapplicationflashlight.model.FlashLightSetting.SECOND;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FlashLightSettingTest {

    private int hour = 23, minute = 17, second = 12;
    private String hourR = "hour(s)", minuteR = "minute(s)", secondR = "second(s)";

    @Test
    public void testConstructor1() throws Exception {
        FlashLightSetting flashLightSetting = new FlashLightSetting(String.format("%d%s%d%s%d%s", hour, HOUR, minute, MINUTE, second, SECOND));
        commonAsserts(flashLightSetting, flashLightSetting.getSeconds(), null, null, null);
        assertEquals("23 hour(s) 17 minute(s) 12 second(s)", flashLightSetting.getTimerName(hourR, minuteR, secondR));
    }

    @Test
    public void testConstructor2() throws Exception {
        FlashLightSetting flashLightSetting = new FlashLightSetting(19, String.format("%d%s%d%s%d%s", hour, HOUR, minute, MINUTE, second, SECOND));
        commonAsserts(flashLightSetting, flashLightSetting.getSeconds(), 19, null, null);
        assertEquals("23 hour(s) 17 minute(s) 12 second(s)", flashLightSetting.getTimerName(hourR, minuteR, secondR));
    }


    @Test
    public void testConstructor3() throws Exception {
        FlashLightSetting flashLightSetting = new FlashLightSetting(19, 0, 15);
        commonAsserts(flashLightSetting, flashLightSetting.getSeconds(), null, "19 " + hourR + " " + 15 + secondR, new Long(19 * 3600 + 15));
    }

    private void commonAsserts(FlashLightSetting flashLightSetting, long seconds, Integer id,
                               String isStand, Long countOfSeconds) {
        String getTimerNameString = "23 hour(s) 17 minute(s) 12 second(s)";
        long countOfSecondsL = TimeUnit.HOURS.toSeconds(hour) + TimeUnit.MINUTES.toSeconds(minute) + second;
        if(null != countOfSeconds){
            countOfSecondsL = countOfSeconds;
        }
        assertEquals(countOfSecondsL, flashLightSetting.getSeconds());
        assertEquals(hour, Integer.parseInt(flashLightSetting.getHour()));
        assertEquals(minute, Integer.parseInt(flashLightSetting.getMinute()));
        assertEquals(second, Integer.parseInt(flashLightSetting.getSecond()));
        assertEquals(id, flashLightSetting.getFlashLightId());
        if (StringUtils.isNotBlank(isStand)) {
            getTimerNameString = isStand;
        }
        assertEquals(getTimerNameString, flashLightSetting.getTimerName(hourR, minuteR, secondR));

    }

}