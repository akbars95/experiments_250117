package camera.android.mtsmda.com.myapplicationflashlight.common.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import camera.android.mtsmda.com.myapplicationflashlight.common.Initialize;
import camera.android.mtsmda.com.myapplicationflashlight.log.L;
import camera.android.mtsmda.com.myapplicationflashlight.log.LogAble;

/**
 * Created by dminzat on 9/4/2017.
 */

public abstract class MyFragmentWithLogger extends MyFragment implements LogAble, Initialize {

    private L l;

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
        this.w(message);
    }
}
