package camera.android.mtsmda.com.myapplicationflashlight.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import camera.android.mtsmda.com.myapplicationflashlight.R;
import camera.android.mtsmda.com.myapplicationflashlight.helper.TimerName;
import camera.android.mtsmda.com.myapplicationflashlight.service.FlashLightService;

import static android.widget.AdapterView.*;

public class MainActivityFlashLight extends AppCompatActivity implements OnItemSelectedListener {

    private static final String TAG = MainActivityFlashLight.class.getCanonicalName();
    private static final String LOCALE_RUS = "ru";

    //languageLevel
    private Button mSwitchLanguageEngButton;
    private Button mSwitchLanguageRusButton;

    //simpleOnOffLevel
    private ToggleButton mOnOffToggleButton;

    //timerFlashLightLevel
    private Button mByTimerButton;
    private Spinner mTimerSpinner;

    private Intent mIntentFlashLightService;

    private List<TimerName> mIntegersForTimeOut;
    private Locale mNewLocale;

    private long mMillisInFuture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_flash_light);

        initUI();
        Log.i(TAG, "on create main activity");
        languageLevel();
        simpleOnOffLevel();
        timerFlashLightLevel();
        defineCurrentLanguage();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "selected position - " + position + ", id is " + id);
        Toast.makeText(parent.getContext(), "Click - " + parent.getItemIdAtPosition(position), Toast.LENGTH_SHORT).show();
        this.mMillisInFuture = mIntegersForTimeOut.get(position).getSeconds();
        Log.i(TAG, "this.mMillisInFuture = " + this.mMillisInFuture);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public Intent getIntentFlashLightService() {
        if (null == this.mIntentFlashLightService) {
            Log.i(TAG, "mIntentFlashLightService is null");
            this.mIntentFlashLightService = new Intent(getBaseContext(), FlashLightService.class);
        }
        Log.i(TAG, "mIntentFlashLightService return");
        return this.mIntentFlashLightService;
    }

    //languageLevel
    private void languageLevel() {
        OnClickListener onClickListener = v -> {
            String message = "Changed language to %s";
            switch (v.getId()) {
                case R.id.switchLanguageRus: {
                    setLocale(LOCALE_RUS);
                    message = String.format(message, LOCALE_RUS);
                }
                break;
                case R.id.switchLanguageEng: {
                    setLocale(Locale.ENGLISH.getLanguage());
                    message = String.format(message, "en");
                }
                break;
                default: {
                    Log.i(TAG, "ERROR! cannot define which button clicked, id = " + v.getId());
                }
                break;
            }
            Log.i(TAG, "mSwitchLanguageEngButton = " + mSwitchLanguageEngButton.isEnabled());
            Log.i(TAG, "mSwitchLanguageRusButton = " + mSwitchLanguageRusButton.isEnabled());
            Log.i(TAG, message);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        };
        this.mSwitchLanguageEngButton.setOnClickListener(onClickListener);
        this.mSwitchLanguageRusButton.setOnClickListener(onClickListener);
    }

    private void defineCurrentLanguage() {
        String language = Locale.getDefault().getLanguage();
        Log.i(TAG, "current language = " + language);
        if (language.equals(LOCALE_RUS)) {
            mSwitchLanguageEngButton.setClickable(true);
            mSwitchLanguageEngButton.setBackgroundColor(Color.GREEN);
            mSwitchLanguageRusButton.setClickable(false);
            mSwitchLanguageRusButton.setBackgroundColor(Color.RED);
        } else {
            mSwitchLanguageEngButton.setClickable(false);
            mSwitchLanguageEngButton.setBackgroundColor(Color.RED);
            mSwitchLanguageRusButton.setClickable(true);
            mSwitchLanguageRusButton.setBackgroundColor(Color.GREEN);
        }
    }

    private void setLocale(String language) {
        Log.i(TAG, "lang = " + language);
        this.mNewLocale = new Locale(language);
        Log.i(TAG, "def lang = " + Locale.getDefault().getLanguage());
        Locale.setDefault(this.mNewLocale);
        Configuration configuration = new Configuration();
        Log.i(TAG, "android current version = " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(this.mNewLocale);
        } else {
            configuration.locale = this.mNewLocale;
        }
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        recreate();
        Log.i(TAG, "Language changed");
        Log.i(TAG, "after changed def lang = " + Locale.getDefault().getLanguage());
    }

    //simpleOnOffLevel
    private void simpleOnOffLevel() {
        this.mOnOffToggleButton.setChecked(false);
        this.mOnOffToggleButton.setBackgroundColor(R.color.colorGreen);
        this.mOnOffToggleButton.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            if (isChecked) {
                startService(getIntentFlashLightService());
                Log.i(TAG, "start service");
                mSwitchLanguageEngButton.setVisibility(View.INVISIBLE);
                mSwitchLanguageRusButton.setVisibility(View.INVISIBLE);
            } else {
                stopService(getIntentFlashLightService());
                Log.i(TAG, "stop service");
                mSwitchLanguageEngButton.setVisibility(View.VISIBLE);
                mSwitchLanguageRusButton.setVisibility(View.VISIBLE);
            }
            mByTimerButton.setEnabled(!isChecked);
            mTimerSpinner.setEnabled(!isChecked);

        });
    }

    //timerFlashLightLevel
    public void timerFlashLightLevel() {
        this.mByTimerButton.setOnClickListener(v -> {
            getCountDownTimer().start();
            Log.i(TAG, "started timer flashlight");
        });

        this.mTimerSpinner.setOnItemSelectedListener(this);
        mIntegersForTimeOut = new ArrayList<TimerName>() {{
            add(new TimerName("10 " + getString(R.string.timer_seconds), TimeUnit.SECONDS.toMillis(10)));
            add(new TimerName("20 " + getString(R.string.timer_seconds), TimeUnit.SECONDS.toMillis(20)));
            add(new TimerName("30 " + getString(R.string.timer_seconds), TimeUnit.SECONDS.toMillis(30)));
            add(new TimerName("45 " + getString(R.string.timer_seconds), TimeUnit.SECONDS.toMillis(45)));
            add(new TimerName("1 " + getString(R.string.timer_minutes), TimeUnit.MINUTES.toMillis(1)));
            add(new TimerName("3 " + getString(R.string.timer_minutes), TimeUnit.MINUTES.toMillis(3)));
            add(new TimerName("5 " + getString(R.string.timer_minutes), TimeUnit.MINUTES.toMillis(5)));
            add(new TimerName("10 " + getString(R.string.timer_minutes), TimeUnit.MINUTES.toMillis(10)));
            add(new TimerName("30 " + getString(R.string.timer_minutes), TimeUnit.MINUTES.toMillis(30)));
            add(new TimerName("1 " + getString(R.string.timer_hours), TimeUnit.HOURS.toMillis(1)));
        }};

        this.mMillisInFuture = mIntegersForTimeOut.get(0).getSeconds();
        Log.i(TAG, "mMillisInFuture = " + mMillisInFuture);

        ArrayAdapter<String> timerNameArrayAdapter;
        Log.i(TAG, "current build version is " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            timerNameArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mIntegersForTimeOut.stream().map(integersForTimeOutL -> integersForTimeOutL.getName()).collect(Collectors.toList()));
            Log.i(TAG, "more or equal 24 api level");
        } else {
            timerNameArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TimerName.getStringNames(mIntegersForTimeOut));
            Log.i(TAG, "less than 24 api level");
        }

        timerNameArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.mTimerSpinner.setAdapter(timerNameArrayAdapter);

        Log.i(TAG, "current local is " + getApplicationContext().getResources().getDisplayMetrics());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.i(TAG, "current local is " + getApplicationContext().getResources().getConfiguration().getLocales().get(0).getCountry());
        }
    }

    private CountDownTimer getCountDownTimer() {
        return new CountDownTimer(this.mMillisInFuture, 1L) {
            int count = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                if (count == 0) {
                    startService(getIntentFlashLightService());
                    mOnOffToggleButton.setEnabled(false);
                    mByTimerButton.setEnabled(false);
                    mTimerSpinner.setEnabled(false);
                    mSwitchLanguageEngButton.setVisibility(View.INVISIBLE);
                    mSwitchLanguageRusButton.setVisibility(View.INVISIBLE);
                }
                Log.i(TAG, "tick " + ++count);
                Log.i(TAG, "millis Until Finished = " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "finish timer");
                stopService(getIntentFlashLightService());
                mOnOffToggleButton.setEnabled(true);
                mByTimerButton.setEnabled(true);
                mTimerSpinner.setEnabled(true);
                mSwitchLanguageEngButton.setVisibility(View.VISIBLE);
                mSwitchLanguageRusButton.setVisibility(View.VISIBLE);
            }
        };
    }

    private void initUI() {
        this.mSwitchLanguageEngButton = getUIElement(R.id.switchLanguageEng, Button.class);
        this.mSwitchLanguageRusButton = getUIElement(R.id.switchLanguageRus, Button.class);
        this.mOnOffToggleButton = getUIElement(R.id.onOffFlashLight, ToggleButton.class);
        this.mTimerSpinner = getUIElement(R.id.timerSpinner, Spinner.class);
        this.mByTimerButton = getUIElement(R.id.byTimer, Button.class);
    }

    private <T> T getUIElement(int id, Class<T> tClass) {
        return (T) findViewById(id);
    }

}