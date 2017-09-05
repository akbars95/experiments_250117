package camera.android.mtsmda.com.myapplicationflashlight.common.activity;

import camera.android.mtsmda.com.myapplicationflashlight.log.L;
import camera.android.mtsmda.com.myapplicationflashlight.log.LogAble;

/**
 * Created by dminzat on 8/31/2017.
 */

public abstract class MyAppCompatActivityWithLogger extends MyAppCompatActivity implements LogAble{

    private L l;

    public MyAppCompatActivityWithLogger() {
        l = new L(this);
    }

    @Override
    public void d(String message, Throwable throwable){
        l.d(message, throwable);
    }

    @Override
    public void d(String message){
        d(message, null);
    }

    @Override
    public void w(String message, Throwable throwable){
        l.w(message, throwable);
    }

    @Override
    public void w(String message){
        w(message, null);
    }

}
