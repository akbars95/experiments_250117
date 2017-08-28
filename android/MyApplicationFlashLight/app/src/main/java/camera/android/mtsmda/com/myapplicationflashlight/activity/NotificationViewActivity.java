package camera.android.mtsmda.com.myapplicationflashlight.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import camera.android.mtsmda.com.myapplicationflashlight.R;

/**
 * Created by dminzat on 8/26/2017.
 */

public class NotificationViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
    }

    public static Intent newIntent(Context context, boolean state){
        Intent intent = new Intent(context, NotificationViewActivity.class);
        intent.putExtra(MainActivityFlashLightActivity.FLASHLIGHT_STATUS, state);
        return intent;
    }

}