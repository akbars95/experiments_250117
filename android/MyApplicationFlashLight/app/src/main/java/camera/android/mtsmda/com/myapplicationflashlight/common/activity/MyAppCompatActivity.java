package camera.android.mtsmda.com.myapplicationflashlight.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import camera.android.mtsmda.com.myapplicationflashlight.common.ElementForActivity;
import camera.android.mtsmda.com.myapplicationflashlight.common.Initialize;

/**
 * Created by dminzat on 8/31/2017.
 */

public abstract class MyAppCompatActivity extends AppCompatActivity implements ElementForActivity, Initialize {

    protected int layoutId;

    @Override
    public <T> T getUIElement(int id, Class<T> tClass) {
        return (T) findViewById(id);
    }

    @Override
    public Button getUIElementButton(int id) {
        return getUIElement(id, Button.class);
    }

    @Override
    public ToggleButton getUIElementToggleButton(int id) {
        return getUIElement(id, ToggleButton.class);
    }

    @Override
    public TextView getUIElementTextView(int id) {
        return getUIElement(id, TextView.class);
    }

    @Override
    public Spinner getUIElementSpinner(int id) {
        return getUIElement(id, Spinner.class);
    }

    @Override
    public EditText getUIElementEditText(int id) {
        return getUIElement(id, EditText.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutId();
        setContentView(layoutId);
        initUI();
    }
}