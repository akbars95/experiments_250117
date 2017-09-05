package camera.android.mtsmda.com.myapplicationflashlight.db;

/**
 * Created by dminzat on 8/28/2017.
 */

public class FlashLightDatabaseSchema {

    public static final class FlashLightTable{
        public static final String TABLE_NAME = "flash_light_timer_settings";

        public static final class Columns{
            public static final String TIMER_SETTINGS_ID = "timer_settings_id";
            public static final String TIMER_SETTINGS_TITLE = "timer_settings_value_of_seconds";
        }
    }

}