package camera.android.mtsmda.com.myapplicationflashlight.activity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.stream.Stream;

import camera.android.mtsmda.com.myapplicationflashlight.R;
import camera.android.mtsmda.com.myapplicationflashlight.common.Helper;
import camera.android.mtsmda.com.myapplicationflashlight.common.activity.MyAppCompatActivityWithLogger;
import camera.android.mtsmda.com.myapplicationflashlight.dao.FlashLightSettingDaoImplDB;
import camera.android.mtsmda.com.myapplicationflashlight.fragment.SpinnerFragment;
import camera.android.mtsmda.com.myapplicationflashlight.model.FlashLightSetting;

/**
 * Created by dminzat on 9/1/2017.
 */

public class SettingActivity extends MyAppCompatActivityWithLogger implements OnClickListener {

    //backLevel
    private Button mGoBackButton;

    //setNewTimerLevel
    private SpinnerFragment mHoursSpinnerFragment = new SpinnerFragment(0, 23, 0);
    private SpinnerFragment mMinutesSpinnerFragment = new SpinnerFragment(0, 59, 0);
    private SpinnerFragment mSecondsSpinnerFragment = new SpinnerFragment(0, 59, 0);

    //addButtonLevel
    private Button mAddNewValueButton;
    private ThreadForUpdateStateButton mThreadForUpdateStateButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        w("SettingActivity.onCreate");

        backLevel();
        addButtonLevel();
//        this.mThreadForUpdateStateButton = new ThreadForUpdateStateButton();
//        this.mThreadForUpdateStateButton.start();
//        this.mThreadForUpdateStateButton.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mThreadForUpdateStateButton.suspend();
        this.mThreadForUpdateStateButton = null;
    }

    public static Intent createIntentSettingActivity(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override
    public void initUI(View... views) {
        w("Init UI");
        this.mGoBackButton = getUIElementButton(R.id.goBack);
        addFragment(R.id.layoutSpinnerHours, mHoursSpinnerFragment);
        addFragment(R.id.layoutSpinnerMinutes, mMinutesSpinnerFragment);
        addFragment(R.id.layoutSpinnerSeconds, mSecondsSpinnerFragment);
        this.mAddNewValueButton = getUIElementButton(R.id.addNewValue);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goBack: {
                w("Go to back to main Activity");
                startActivity(MainActivityFlashLightActivity.getIntent(getApplicationContext()));
            }
            break;
            case R.id.addNewValue: {
            w("add new value");
                /*if(Helper.isHasInVersion(Build.VERSION_CODES.N)){
                    if(Stream.of(mHoursSpinnerFragment, mMinutesSpinnerFragment, mSecondsSpinnerFragment).
                            anyMatch(SpinnerFragment::isCurrentValueNotEqualNull)){

                    }
                }

                if(mHoursSpinnerFragment.isCurrentValueEqualNull()){
//                    Helper.showToastShort(getApplicationContext(), );
                }*/
                if(mHoursSpinnerFragment.isCurrentValueEqualNull()){
//                    Helper.showToastShort(getApplicationContext(), );
                }
                FlashLightSettingDaoImplDB.getInstance(getApplicationContext())
                        .insertFlashLightSetting(
                                new FlashLightSetting(
                                        mHoursSpinnerFragment.getCurrentValue(),
                                        mMinutesSpinnerFragment.getCurrentValue(),
                                        mSecondsSpinnerFragment.getCurrentValue()));
            }
            break;
        }
    }

    @Override
    public void setLayoutId() {
        this.layoutId = R.layout.settings_layout;
    }

    private void addButtonLevel() {
        this.mAddNewValueButton.setOnClickListener(this);
    }

    private void backLevel() {
        w("Init back level");
        this.mGoBackButton.setOnClickListener(this);
    }

    private void addFragment(int fragmentId, SpinnerFragment spinnerFragment) {
        FragmentManager fragmentManager = getFragmentManager();
        w("FragmentManager = " + (null == fragmentManager));
        fragmentManager.beginTransaction().replace(fragmentId, spinnerFragment).commit();
        w("added fragment");
    }

    private class ThreadForUpdateStateButton implements Runnable {
        private Thread mThread;
        private boolean suspended;

        @Override
        public void run() {
            try {
                for (int i = 0; i < 1_000_000_000; i++) {
                    w("suspended = " + suspended);
                    Thread.sleep(250);
                    synchronized (this) {
                        while (!suspended) {
                            w("thread waiting");
                            wait();
                        }
                    }

                    w("for i = " + i);
                    boolean buttonIsEnable = mHoursSpinnerFragment.isCurrentValueNotEqualNull() || mMinutesSpinnerFragment.isCurrentValueNotEqualNull() || mSecondsSpinnerFragment.isCurrentValueNotEqualNull();
                    w("buttonIsEnable = " + buttonIsEnable);
                    mAddNewValueButton.setEnabled(buttonIsEnable);
                    if (buttonIsEnable) {
                        suspend();
                    }
                }
                w("Stop run!");
            } catch (InterruptedException e) {
                w("interrupt on run method", e);
            }

        }

        public void start() {
            w("start thread method");
            if (null == mThread) {
                this.mThread = new Thread(this, "button state control thread");
                this.mThread.start();
                w("thread started");
            }
        }

        public void suspend() {
            suspended = false;
        }

        public synchronized void resume() {
            suspended = true;
            notify();
        }

    }

}