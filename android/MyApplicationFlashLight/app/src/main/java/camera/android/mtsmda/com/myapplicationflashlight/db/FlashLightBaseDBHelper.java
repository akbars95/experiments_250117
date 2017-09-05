package camera.android.mtsmda.com.myapplicationflashlight.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import camera.android.mtsmda.com.myapplicationflashlight.dao.FlashLightSettingDao;
import camera.android.mtsmda.com.myapplicationflashlight.dao.FlashLightSettingDaoImplDB;
import camera.android.mtsmda.com.myapplicationflashlight.log.L;
import camera.android.mtsmda.com.myapplicationflashlight.model.FlashLightSetting;

/**
 * Created by dminzat on 8/28/2017.
 */

public class FlashLightBaseDBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "flash_light.db";
    private static int onCreate;

    private FlashLightSettingDao mFlashLightSettingDao;
    private SQLiteDatabase writableDatabase;
    private L l;

    public FlashLightBaseDBHelper(Context context) {
        this(context, (DatabaseErrorHandler)null);
    }

    public FlashLightBaseDBHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, null, VERSION, errorHandler);
        this.l = new L(this);
    }

    public FlashLightBaseDBHelper(Context context, DatabaseErrorHandler errorHandler, FlashLightSettingDao mFlashLightSettingDao) {
        this(context);
        this.mFlashLightSettingDao = mFlashLightSettingDao;
        l.w("this.mFlashLightSettingDao = " + (null == this.mFlashLightSettingDao));
    }

    public FlashLightBaseDBHelper(Context context, FlashLightSettingDao mFlashLightSettingDao) {
        this(context, null, mFlashLightSettingDao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("create table %s(%s integer primary key autoincrement,%s)",
                FlashLightDatabaseSchema.FlashLightTable.TABLE_NAME,
                FlashLightDatabaseSchema.FlashLightTable.Columns.TIMER_SETTINGS_ID,
                FlashLightDatabaseSchema.FlashLightTable.Columns.TIMER_SETTINGS_TITLE));
        l.w("db.getPath() = " + db.getPath());
        l.w("db.toString() = " + db.toString());
        l.w("db.getMaximumSize() = " + db.getMaximumSize());
        l.w("onCreate = " + (++onCreate));
        l.w("writableDatabase is null - " + (null == writableDatabase));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static boolean isRunOnCreate(){
        return onCreate == 1;
    }

    private void insertInitData(FlashLightSetting flashLightSetting){
        getSQLiteDatabase().insert(FlashLightDatabaseSchema.FlashLightTable.TABLE_NAME, null, FlashLightSettingDaoImplDB.getContentValues(flashLightSetting));
    }

    private SQLiteDatabase getSQLiteDatabase(){
        if(null == writableDatabase){
            writableDatabase = this.getWritableDatabase();
            l.w("writableDatabase is null");
        }
        l.w("writableDatabase is Not null");
        return writableDatabase;
    }

}