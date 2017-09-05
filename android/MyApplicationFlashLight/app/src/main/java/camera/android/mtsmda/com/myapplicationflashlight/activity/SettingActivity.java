package camera.android.mtsmda.com.myapplicationflashlight.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import camera.android.mtsmda.com.myapplicationflashlight.R;
import camera.android.mtsmda.com.myapplicationflashlight.common.activity.MyAppCompatActivityWithLogger;
import camera.android.mtsmda.com.myapplicationflashlight.fragment.SpinnerFragment;

/**
 * Created by dminzat on 9/1/2017.
 */

public class SettingActivity extends MyAppCompatActivityWithLogger {

    //backLevel
    private Button mGoBackButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        backLevel();
        workWithFragment();
    }

    public static Intent createIntentSettingActivity(Context context) {
        return new Intent(context, SettingActivity.class);
    }

    @Override
    public void initUI(View... views) {
        w("Init UI");
        this.mGoBackButton = getUIElementButton(R.id.goBack);
    }

    @Override
    public void setLayoutId() {
        this.layoutId = R.layout.settings_layout;
    }

    private void backLevel() {
        w("Init back level");
        this.mGoBackButton.setOnClickListener(v -> {
            w("Go to back to main Activity");
            startActivity(MainActivityFlashLightActivity.getIntent(getApplicationContext()));
        });
    }

    private void workWithFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        w("FragmentManager = " + (null == fragmentManager));
        Fragment fragment = fragmentManager.findFragmentById(R.layout.spinner_fragment);
        if (null == fragment) {
            w("null == fragment");
            fragment = new SpinnerFragment();
            fragmentManager.beginTransaction().add(R.layout.spinner_fragment, fragment).commit();
            w("added fragment");
        }
    }
}