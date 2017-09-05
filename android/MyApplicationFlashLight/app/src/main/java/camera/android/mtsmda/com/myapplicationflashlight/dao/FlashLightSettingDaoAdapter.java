package camera.android.mtsmda.com.myapplicationflashlight.dao;

import android.content.Context;

import java.util.List;

import camera.android.mtsmda.com.myapplicationflashlight.log.L;
import camera.android.mtsmda.com.myapplicationflashlight.model.FlashLightSetting;

/**
 * Created by dminzat on 8/28/2017.
 */

public abstract class FlashLightSettingDaoAdapter implements FlashLightSettingDao {

    protected Context mContext;
    protected L l;

    public FlashLightSettingDaoAdapter(Context context) {
        this.mContext = context;
        this.l = new L(this);
    }

    @Override
    public void insertFlashLightSetting(FlashLightSetting flashLightSetting) {

    }

    @Override
    public void updateFlashLightSetting(FlashLightSetting flashLightSetting) {

    }

    @Override
    public List<FlashLightSetting> getAllFlashLightSettings() {
        return null;
    }
}
