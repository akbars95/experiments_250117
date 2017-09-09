package camera.android.mtsmda.com.myapplicationflashlight.common;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

/**
 * Created by dminzat on 9/8/2017.
 */

public class Helper {

    public static void showToastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static boolean isHasInVersion(int version){
        return Build.VERSION.SDK_INT >= version;
    }

}