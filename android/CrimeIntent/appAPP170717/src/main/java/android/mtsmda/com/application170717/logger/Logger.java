package android.mtsmda.com.application170717.logger;

import android.util.Log;

import java.util.Objects;

/**
 * Created by dminzat on 7/27/2017.
 */

public class Logger {

    public static final void info(String tag, String msg, Object ... params) {
        Log.i(tag, String.format(msg, params));
    }

    public static final void verbose(String tag, String msg, Object ... params) {
        Log.v(tag, String.format(msg, params));
    }

    public static final void debug(String tag, String msg, Object ... params) {
        Log.d(tag, String.format(msg, params));
    }

    public static final void warn(String tag, String msg, Object ... params) {
        Log.w(tag, String.format(msg, params));
    }

    public static final void error(String tag, String msg, Object ... params) {
        Log.e(tag, String.format(msg, params));
    }

}