package camera.android.mtsmda.com.myapplicationflashlight.dao;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import camera.android.mtsmda.com.myapplicationflashlight.model.FlashLightSetting;

/**
 * Created by dminzat on 8/28/2017.
 */

public class FlashLightSettingDaoImplInMemory extends FlashLightSettingDaoAdapter{

    public FlashLightSettingDaoImplInMemory(Context context) {
        super(context);
    }

    @Override
    public List<FlashLightSetting> getAllFlashLightSettings() {
        return new ArrayList<FlashLightSetting>() {{
            /*add(new FlashLightSetting(new TimerName("10 " + mContext.getString(R.string.timer_seconds), TimeUnit.SECONDS.toMillis(10))));
            add(new FlashLightSetting(new TimerName("20 " + mContext.getString(R.string.timer_seconds), TimeUnit.SECONDS.toMillis(20))));
            add(new FlashLightSetting(new TimerName("30 " + mContext.getString(R.string.timer_seconds), TimeUnit.SECONDS.toMillis(30))));
            add(new FlashLightSetting(new TimerName("45 " + mContext.getString(R.string.timer_seconds), TimeUnit.SECONDS.toMillis(45))));
            add(new FlashLightSetting(new TimerName("1 " + mContext.getString(R.string.timer_minutes), TimeUnit.MINUTES.toMillis(1))));
            add(new FlashLightSetting(new TimerName("3 " + mContext.getString(R.string.timer_minutes), TimeUnit.MINUTES.toMillis(3))));
            add(new FlashLightSetting(new TimerName("5 " + mContext.getString(R.string.timer_minutes), TimeUnit.MINUTES.toMillis(5))));
            add(new FlashLightSetting(new TimerName("10 " + mContext.getString(R.string.timer_minutes), TimeUnit.MINUTES.toMillis(10))));
            add(new FlashLightSetting(new TimerName("30 " + mContext.getString(R.string.timer_minutes), TimeUnit.MINUTES.toMillis(30))));
            add(new FlashLightSetting(new TimerName("1 " + mContext.getString(R.string.timer_hours), TimeUnit.HOURS.toMillis(1))));*/
        }};
    }

}