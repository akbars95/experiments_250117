package camera.android.mtsmda.com.myapplicationflashlight.common.fragment;

import camera.android.mtsmda.com.myapplicationflashlight.common.Initialize;
import camera.android.mtsmda.com.myapplicationflashlight.log.L;
import camera.android.mtsmda.com.myapplicationflashlight.log.LogAble;

/**
 * Created by dminzat on 9/4/2017.
 */

public abstract class MyFragmentWithLogger extends MyFragment implements LogAble, Initialize {

    private L l;

    public MyFragmentWithLogger() {
        this.l = new L(this);
    }

    @Override
    public void d(String message, Throwable throwable) {
        l.d(message, throwable);
    }

    @Override
    public void d(String message) {
        this.d(message, null);
    }

    @Override
    public void w(String message, Throwable throwable) {
        l.w(message, throwable);
    }

    @Override
    public void w(String message) {
        this.w(message, null);
    }
}
