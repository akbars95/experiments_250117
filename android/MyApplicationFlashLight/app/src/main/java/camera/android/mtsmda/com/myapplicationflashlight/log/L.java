package camera.android.mtsmda.com.myapplicationflashlight.log;

import android.util.Log;

/**
 * Created by dminzat on 8/29/2017.
 */

public class L implements LogAble{

    private String tagName;

    public  <T> L(T t){
        tagName = t.getClass().getSimpleName();
    }

    public  <T> L(Class<T> t){
        tagName = t.getSimpleName();
    }

    public void d(String message, Throwable throwable){
        Log.d(tagName, message, throwable);
    }

    public void d(String message){
        d(message, null);
    }

    public void w(String message, Throwable throwable){
        Log.w(tagName, message, throwable);
    }

    public void w(String message){
        w(message, null);
    }

    public String getTagName() {
        return tagName;
    }
}