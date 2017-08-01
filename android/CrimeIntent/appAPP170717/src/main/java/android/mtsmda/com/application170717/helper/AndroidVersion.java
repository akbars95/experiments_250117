package android.mtsmda.com.application170717.helper;

import android.os.Build;

/**
 * Created by dminzat on 7/27/2017.
 */

public class AndroidVersion {

    public static String getCodeName(int api) {
        String name = null;
        switch (api) {
            case Build.VERSION_CODES.LOLLIPOP:
                name = "LOLLIPOP";
                break;
            case Build.VERSION_CODES.ECLAIR_MR1:
                name = "ECLAIR_MR1";
                break;
            case Build.VERSION_CODES.FROYO:
                name = "FROYO";
                break;
            case Build.VERSION_CODES.HONEYCOMB:
                name = "HONEYCOMB";
                break;
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH:
                name = "ICE_CREAM_SANDWICH";
                break;
            case Build.VERSION_CODES.JELLY_BEAN:
                name = "JELLY_BEAN";
                break;
            case Build.VERSION_CODES.KITKAT:
                name = "KITKAT";
                break;
        }
        return api + " " + name;
    }

}
