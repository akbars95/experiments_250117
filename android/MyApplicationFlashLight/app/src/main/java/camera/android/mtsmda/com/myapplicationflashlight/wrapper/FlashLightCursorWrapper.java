package camera.android.mtsmda.com.myapplicationflashlight.wrapper;

import android.database.Cursor;
import android.database.CursorWrapper;

import camera.android.mtsmda.com.myapplicationflashlight.db.FlashLightDatabaseSchema.FlashLightTable;
import camera.android.mtsmda.com.myapplicationflashlight.model.FlashLightSetting;

/**
 * Created by dminzat on 8/28/2017.
 */

public class FlashLightCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public FlashLightCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public FlashLightSetting getFlashLightSetting() {
        return new FlashLightSetting(getInt(getColumnIndex(FlashLightTable.Columns.TIMER_SETTINGS_ID)), getString(getColumnIndex(FlashLightTable.Columns.TIMER_SETTINGS_TITLE)));
    }

}
