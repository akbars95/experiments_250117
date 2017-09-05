package camera.android.mtsmda.com.myapplicationflashlight.dao;

import android.content.Context;

import java.util.List;

import camera.android.mtsmda.com.myapplicationflashlight.model.FlashLightSetting;

/**
 * Created by dminzat on 8/28/2017.
 */

public interface FlashLightSettingDao {

    void insertFlashLightSetting(FlashLightSetting flashLightSetting);
    void updateFlashLightSetting(FlashLightSetting flashLightSetting);
    List<FlashLightSetting> getAllFlashLightSettings();

}
