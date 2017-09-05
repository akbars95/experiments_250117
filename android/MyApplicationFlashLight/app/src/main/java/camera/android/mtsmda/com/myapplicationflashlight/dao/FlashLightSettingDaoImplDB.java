package camera.android.mtsmda.com.myapplicationflashlight.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import camera.android.mtsmda.com.myapplicationflashlight.db.FlashLightBaseDBHelper;
import camera.android.mtsmda.com.myapplicationflashlight.db.FlashLightDatabaseSchema.FlashLightTable;
import camera.android.mtsmda.com.myapplicationflashlight.log.L;
import camera.android.mtsmda.com.myapplicationflashlight.model.FlashLightSetting;
import camera.android.mtsmda.com.myapplicationflashlight.wrapper.FlashLightCursorWrapper;

/**
 * Created by dminzat on 8/28/2017.
 */

public class FlashLightSettingDaoImplDB extends FlashLightSettingDaoAdapter {

    private SQLiteDatabase mSQLiteDatabase;

    public FlashLightSettingDaoImplDB(Context context) {
        super(context);
        this.mSQLiteDatabase = new FlashLightBaseDBHelper(mContext, this).getWritableDatabase();
    }

    @Override
    public void insertFlashLightSetting(FlashLightSetting flashLightSetting) {
        l.w("inserted result = " + mSQLiteDatabase.insert(FlashLightTable.TABLE_NAME, null, getContentValues(flashLightSetting)));
    }

    @Override
    public void updateFlashLightSetting(FlashLightSetting flashLightSetting) {
        l.w("updated result = " + mSQLiteDatabase.update(FlashLightTable.TABLE_NAME, getContentValues(flashLightSetting), FlashLightTable.Columns.TIMER_SETTINGS_ID + " = ?",
                new String[]{flashLightSetting.getFlashLightId().toString()}));
    }

    @Override
    public List<FlashLightSetting> getAllFlashLightSettings() {
        List<FlashLightSetting> flashLightSettings = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try (FlashLightCursorWrapper flashLightCursorWrapper = new FlashLightCursorWrapper(mSQLiteDatabase.query(FlashLightTable.TABLE_NAME, null, null, null, null, null, null));) {
                innerReading(flashLightCursorWrapper, flashLightSettings);
                l.w("java 7 throw with params");
            } catch (Exception e) {
                l.w("eception when reading data", e);
            }
        } else {
            FlashLightCursorWrapper flashLightCursorWrapper = null;
            try {
                flashLightCursorWrapper = new FlashLightCursorWrapper(mSQLiteDatabase.query(FlashLightTable.TABLE_NAME, null, null, null, null, null, null));
                innerReading(flashLightCursorWrapper, flashLightSettings);
                l.w("before java 7");
            } catch (Exception e) {
                l.w("eception when reading data", e);
            } finally {
                flashLightCursorWrapper.close();
            }
        }
        l.w("get all size is " + (flashLightSettings.size()));
        return flashLightSettings;
    }

    private void innerReading(FlashLightCursorWrapper flashLightCursorWrapper, List<FlashLightSetting> flashLightSettings) {
        flashLightCursorWrapper.moveToFirst();
        while (!flashLightCursorWrapper.isAfterLast()) {
            flashLightSettings.add(flashLightCursorWrapper.getFlashLightSetting());
            flashLightCursorWrapper.moveToNext();
        }
    }

    public static ContentValues getContentValues(FlashLightSetting flashLightSetting) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(FlashLightTable.Columns.TIMER_SETTINGS_ID, flashLightSetting.getFlashLightId());
        contentValues.put(FlashLightTable.Columns.TIMER_SETTINGS_TITLE, flashLightSetting.getTimerValue());
        return contentValues;
    }

}