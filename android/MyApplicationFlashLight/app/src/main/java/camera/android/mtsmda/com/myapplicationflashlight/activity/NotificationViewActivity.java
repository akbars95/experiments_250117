package camera.android.mtsmda.com.myapplicationflashlight.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import camera.android.mtsmda.com.myapplicationflashlight.R;
import camera.android.mtsmda.com.myapplicationflashlight.common.activity.MyAppCompatActivityWithLogger;

/**
 * Created by dminzat on 8/26/2017.
 */

public class NotificationViewActivity extends MyAppCompatActivityWithLogger {

    public NotificationViewActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        w("create Notification activity");
    }

    public static Intent newIntent(Context context, boolean state){
        Intent intent = new Intent(context, NotificationViewActivity.class);
        intent.putExtra(MainActivityFlashLightActivity.FLASHLIGHT_STATUS, state);
        return intent;
    }

    @Override
    public void initUI(View... views) {

    }

    @Override
    public void setLayoutId() {
        this.layoutId = R.layout.notification;
    }
}