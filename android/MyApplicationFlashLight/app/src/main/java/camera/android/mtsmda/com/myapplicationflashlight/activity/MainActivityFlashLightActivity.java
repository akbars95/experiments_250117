package camera.android.mtsmda.com.myapplicationflashlight.activity;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import camera.android.mtsmda.com.myapplicationflashlight.R;
import camera.android.mtsmda.com.myapplicationflashlight.common.activity.MyAppCompatActivityWithLogger;
import camera.android.mtsmda.com.myapplicationflashlight.dao.FlashLightSettingDaoImplDB;
import camera.android.mtsmda.com.myapplicationflashlight.db.FlashLightBaseDBHelper;
import camera.android.mtsmda.com.myapplicationflashlight.model.FlashLightSetting;
import camera.android.mtsmda.com.myapplicationflashlight.service.FlashLightService;

import static android.widget.AdapterView.OnItemSelectedListener;

public class MainActivityFlashLightActivity extends MyAppCompatActivityWithLogger implements OnItemSelectedListener {

    private static final String LOCALE_RUS = "ru";
    private static final Integer NOTIFICATION_START_FLASHLIGHT_ID = 0;
    public static final String FLASHLIGHT_STATUS = "com.mtsmda.android.flashlight.status";

    //languageLevel
    private Button mSwitchLanguageEngButton;
    private Button mSwitchLanguageRusButton;

    //simpleOnOffLevel
    private Button mOnOffFlashLightButton;
    private Button mRunSOSButton;

    //timerFlashLightLevel
    private Button mByTimerButton;
    private Spinner mTimerSpinner;

    //settingsLevel
    private Button mSettingsButton;

    private Intent mIntentFlashLightService;
    private NotificationManagerCompat notificationManagerCompat;

    private Locale mNewLocale;

    private long mMillisInFuture;
    private boolean currentIsOnFlashLight;
    private boolean sosRun;
    private List<FlashLightSetting> allFlashLightSettings;
    private ThreadForSosButton threadForSosButton;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        controlFlashLightStatus(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        w("layout id is = " + this.layoutId);
        super.onCreate(savedInstanceState);
        w("on create main activity");
        w("savedInstanceState is null " + (savedInstanceState == null));
        controlFlashLightStatus(savedInstanceState);

        this.threadForSosButton = new ThreadForSosButton();
        this.threadForSosButton.start();
        w("started thread for sos button");

        insertToDB();
        languageLevel();
        simpleOnOffLevel();
        timerFlashLightLevel();
        defineCurrentLanguage();
        settingsLevel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.threadForSosButton.suspend();
        this.threadForSosButton = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        w("selected position - " + position + ", id is " + id);
        this.mMillisInFuture = TimeUnit.SECONDS.toMillis(allFlashLightSettings.get(position).getSeconds());
        w("this.mMillisInFuture = " + this.mMillisInFuture);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public Intent getIntentFlashLightService() {
        if (null == this.mIntentFlashLightService) {
            w("mIntentFlashLightService is null");
            this.mIntentFlashLightService = new Intent(getBaseContext(), FlashLightService.class);
        }
        w("mIntentFlashLightService return");
        return this.mIntentFlashLightService;
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, MainActivityFlashLightActivity.class);
    }

    private void insertToDB() {
        w("FlashLightBaseDBHelper.onCreateRan " + FlashLightBaseDBHelper.isRunOnCreate());
        if (FlashLightBaseDBHelper.isRunOnCreate()) {
            FlashLightSettingDaoImplDB.getInstance(getApplicationContext()).insertFlashLightSetting(new FlashLightSetting(0, 0, 10));
            FlashLightSettingDaoImplDB.getInstance(getApplicationContext()).insertFlashLightSetting(new FlashLightSetting(0, 0, 20));
            FlashLightSettingDaoImplDB.getInstance(getApplicationContext()).insertFlashLightSetting(new FlashLightSetting(0, 0, 30));
            FlashLightSettingDaoImplDB.getInstance(getApplicationContext()).insertFlashLightSetting(new FlashLightSetting(0, 0, 45));
            FlashLightSettingDaoImplDB.getInstance(getApplicationContext()).insertFlashLightSetting(new FlashLightSetting(0, 1, 0));
            FlashLightSettingDaoImplDB.getInstance(getApplicationContext()).insertFlashLightSetting(new FlashLightSetting(0, 2, 0));
            FlashLightSettingDaoImplDB.getInstance(getApplicationContext()).insertFlashLightSetting(new FlashLightSetting(0, 3, 0));
            FlashLightSettingDaoImplDB.getInstance(getApplicationContext()).insertFlashLightSetting(new FlashLightSetting(0, 5, 0));
            FlashLightSettingDaoImplDB.getInstance(getApplicationContext()).insertFlashLightSetting(new FlashLightSetting(0, 10, 0));
            FlashLightSettingDaoImplDB.getInstance(getApplicationContext()).insertFlashLightSetting(new FlashLightSetting(0, 30, 0));
            FlashLightSettingDaoImplDB.getInstance(getApplicationContext()).insertFlashLightSetting(new FlashLightSetting(1, 0, 0));
            FlashLightSettingDaoImplDB.getInstance(getApplicationContext()).insertFlashLightSetting(new FlashLightSetting(1, 0, 5));
        }
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
                    w("ERROR! cannot define which button clicked, id = " + v.getId());
                }
                break;
            }
            w("mSwitchLanguageEngButton = " + mSwitchLanguageEngButton.isEnabled());
            w("mSwitchLanguageRusButton = " + mSwitchLanguageRusButton.isEnabled());
            w(message);
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        };
        w("Is Null = " + (null == this.mSwitchLanguageEngButton));
        this.mSwitchLanguageEngButton.setOnClickListener(onClickListener);
        this.mSwitchLanguageRusButton.setOnClickListener(onClickListener);
    }

    private void defineCurrentLanguage() {
        String language = Locale.getDefault().getLanguage();
        w("current language = " + language);
        if (language.equals(LOCALE_RUS)) {
            mSwitchLanguageEngButton.setClickable(true);
            mSwitchLanguageEngButton.setBackgroundColor(Color.GREEN);
            mSwitchLanguageEngButton.setTextColor(Color.RED);
            mSwitchLanguageRusButton.setClickable(false);
            mSwitchLanguageRusButton.setBackgroundColor(Color.RED);
            mSwitchLanguageRusButton.setTextColor(Color.GREEN);
        } else {
            mSwitchLanguageEngButton.setClickable(false);
            mSwitchLanguageEngButton.setBackgroundColor(Color.RED);
            mSwitchLanguageEngButton.setTextColor(Color.GREEN);
            mSwitchLanguageRusButton.setClickable(true);
            mSwitchLanguageRusButton.setBackgroundColor(Color.GREEN);
            mSwitchLanguageRusButton.setTextColor(Color.RED);
        }
    }

    private void controlFlashLightStatus(Bundle bundle) {
        w("null != bundle - " + (null != bundle));
        if (null != bundle) {
            currentIsOnFlashLight = bundle.getBoolean(FLASHLIGHT_STATUS);
            w("currentIsOnFlashLight on controlFlashLightStatus = " + currentIsOnFlashLight);
        }
    }

    private void setLocale(String language) {
        w("lang = " + language);
        this.mNewLocale = new Locale(language);
        w("def lang = " + Locale.getDefault().getLanguage());
        Locale.setDefault(this.mNewLocale);
        Configuration configuration = new Configuration();
        w("android current version = " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(this.mNewLocale);
        } else {
            configuration.locale = this.mNewLocale;
        }
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        recreate();
        w("Language changed");
        w("after changed def lang = " + Locale.getDefault().getLanguage());
    }

    //simpleOnOffLevel
    private void simpleOnOffLevel() {
        w("currentIsOnFlashLight = " + currentIsOnFlashLight);
        this.mOnOffFlashLightButton.setText(currentIsOnFlashLight ? R.string.stop : R.string.start);
        this.mOnOffFlashLightButton.setOnClickListener(v -> {
            w("currentIsOnFlashLight = " + currentIsOnFlashLight);
            if (currentIsOnFlashLight) {
                startService(getIntentFlashLightService());
                w("start service");
                mSwitchLanguageEngButton.setVisibility(View.INVISIBLE);
                mSwitchLanguageRusButton.setVisibility(View.INVISIBLE);
                addNotification();
            } else {
                stopService(getIntentFlashLightService());
                w("stop service");
                mSwitchLanguageEngButton.setVisibility(View.VISIBLE);
                mSwitchLanguageRusButton.setVisibility(View.VISIBLE);
                if(null != notificationManagerCompat){
                    notificationManagerCompat.cancel(NOTIFICATION_START_FLASHLIGHT_ID);
                }
            }
            mByTimerButton.setEnabled(!currentIsOnFlashLight);
            mTimerSpinner.setEnabled(!currentIsOnFlashLight);
            mOnOffFlashLightButton.setText(currentIsOnFlashLight ? R.string.stop : R.string.start);
            currentIsOnFlashLight = !currentIsOnFlashLight;
            mOnOffFlashLightButton.setClickable(false);
            w("mOnOffFlashLightButton.setClickable(false);");
            try {
                w("sleep to 900 sm");
                Thread.sleep(900);
                w("wake up after 900 ms sleeping");
            } catch (InterruptedException e) {
                w("sleep exception", e);
            }
            mOnOffFlashLightButton.setClickable(true);
            w("mOnOffFlashLightButton.setClickable(true);");
        });

        managementSosButtonState();
        w("init sos button state");
        this.mRunSOSButton.setOnClickListener(v -> {
            managementSosButtonState();
            w("onclick to sos button");
        });

    }

    private void managementSosButtonState() {
        if (sosRun) {
            mRunSOSButton.setText(getString(R.string.disableSos));
            threadForSosButton.resume();
            w("resume");
        } else {
            mRunSOSButton.setText(getString(R.string.runSos));
            threadForSosButton.suspend();
            w("suspend");
        }
        sosRun = !sosRun;
    }

    //timerFlashLightLevel
    public void timerFlashLightLevel() {
        this.mByTimerButton.setOnClickListener(v -> {
            getCountDownTimer().start();
            w("started timer flashlight");
        });

        this.mTimerSpinner.setOnItemSelectedListener(this);
        this.allFlashLightSettings = FlashLightSettingDaoImplDB.getInstance(getApplicationContext()).getAllFlashLightSettings();
        w("null == allFlashLightSettings - " + (null == this.allFlashLightSettings));
        w("allFlashLightSettings.size() - " + (this.allFlashLightSettings.size()));
        this.mMillisInFuture = TimeUnit.SECONDS.toMillis(this.allFlashLightSettings.get(0).getSeconds());
        w("allFlashLightSettings.get(0).getTimerName().getSeconds() - " + (this.allFlashLightSettings.get(0).getTimerName(getString(R.string.timer_hours), getString(R.string.timer_minutes),
                getString(R.string.timer_seconds))));
        w("mMillisInFuture = " + mMillisInFuture);

        ArrayAdapter<String> timerNameArrayAdapter;
        w("current build version is " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            timerNameArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, this.allFlashLightSettings.stream()
                    .map(integersForTimeOutL -> integersForTimeOutL.getTimerName(getString(R.string.timer_hours), getString(R.string.timer_minutes), getString(R.string.timer_seconds))).collect(Collectors.toList()));
            w("more or equal 24 api level");
        } else {
            List<String> stringNames = new ArrayList<>();
            for (FlashLightSetting flashLightSetting : this.allFlashLightSettings) {
                stringNames.add(flashLightSetting.getTimerName(getString(R.string.timer_hours), getString(R.string.timer_minutes), getString(R.string.timer_seconds)));
            }
            for (String s : stringNames) {
                w(s + " = s");
            }
            timerNameArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, stringNames);
            w("less than 24 api level");
        }

        timerNameArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        w("timerNameArrayAdapter is null " + (timerNameArrayAdapter == null));
        this.mTimerSpinner.setAdapter(timerNameArrayAdapter);

        w("current local is " + getApplicationContext().getResources().getDisplayMetrics());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            w("current local is " + getApplicationContext().getResources().getConfiguration().getLocales().get(0).getCountry());
        }
    }

    private CountDownTimer getCountDownTimer() {
        return new CountDownTimer(this.mMillisInFuture, 1L) {
            private int count = 0;
            private GregorianCalendar mStartGregorianCalendar;

            @Override
            public void onTick(long millisUntilFinished) {
                if (count == 0) {
                    startService(getIntentFlashLightService());
                    mOnOffFlashLightButton.setEnabled(false);
                    mByTimerButton.setEnabled(false);
                    mTimerSpinner.setEnabled(false);
                    mSwitchLanguageEngButton.setVisibility(View.INVISIBLE);
                    mSwitchLanguageRusButton.setVisibility(View.INVISIBLE);
                    mStartGregorianCalendar = new GregorianCalendar();
                }
                w("tick " + ++count);
                w("millis Until Finished = " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                w("finish timer");
                stopService(getIntentFlashLightService());
                mOnOffFlashLightButton.setEnabled(true);
                mByTimerButton.setEnabled(true);
                mTimerSpinner.setEnabled(true);
                mSwitchLanguageEngButton.setVisibility(View.VISIBLE);
                mSwitchLanguageRusButton.setVisibility(View.VISIBLE);
                w("start is " + mStartGregorianCalendar.toString());
                w("stop is " + new GregorianCalendar().toString());
            }
        };
    }

    @Override
    public void initUI(View... views) {
        this.mSwitchLanguageEngButton = getUIElementButton(R.id.switchLanguageEng);
        this.mSwitchLanguageRusButton = getUIElementButton(R.id.switchLanguageRus);
        this.mOnOffFlashLightButton = getUIElementButton(R.id.onOffFlashLight);
        this.mRunSOSButton = getUIElementButton(R.id.runSos);
        this.mTimerSpinner = getUIElementSpinner(R.id.timerSpinner);
        this.mByTimerButton = getUIElementButton(R.id.byTimer);
        this.mSettingsButton = getUIElementButton(R.id.goToSettings);
        w("this.mSettingsButton is null " + (null == this.mSettingsButton));
        w("Initialled UI");
    }

    @Override
    public void setLayoutId() {
        this.layoutId = R.layout.activity_main_flash_light;
        w("layout id is setLayoutId = " + this.layoutId);
    }

    private void addNotification() {
        w("Notification started");
        Bundle bundle = new Bundle();
        bundle.putBoolean(FLASHLIGHT_STATUS, currentIsOnFlashLight);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, (int) System.currentTimeMillis(), NotificationViewActivity.newIntent(this, currentIsOnFlashLight), 0, bundle);
        Notification notification =
                new NotificationCompat.Builder(this)
                        .setContentTitle("Notification")
                        .setContentText("This is a test notification")
                        .setSmallIcon(R.drawable.icon)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .addAction(R.drawable.icon, "Call", pendingIntent)
                        .build();

        notificationManagerCompat =
                NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_START_FLASHLIGHT_ID, notification);
    }

    private void settingsLevel() {
        w("settings level begin");
        this.mSettingsButton.setOnClickListener(v -> {
            w("Go to the settings activity");
            startActivity(SettingActivity.createIntentSettingActivity(getApplicationContext()));
        });
    }

    private class ThreadForSosButton implements Runnable {
        private Thread mThread;
        private boolean suspended;

        @Override
        public void run() {
            try {
                for (int i = 0; i < 1_000_000_000; i++) {
                    w("suspended = " + suspended);
                    synchronized (this) {
                        while (!suspended) {
                            w("thread waiting");
                            wait();
                        }
                    }

                    w("for i = " + i);
                    Thread.sleep(500);
                    startService(getIntentFlashLightService());
                    Thread.sleep(500);
                    stopService(getIntentFlashLightService());
                    w("Run run");
                }
                w("Stop run!");
            } catch (InterruptedException e) {
                w("interrupt on run method", e);
            }

        }

        public void start() {
            w("start thread method");
            if (null == mThread) {
                this.mThread = new Thread(this, "sos button thread");
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