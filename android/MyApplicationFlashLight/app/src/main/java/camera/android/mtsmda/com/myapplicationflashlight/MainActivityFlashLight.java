package camera.android.mtsmda.com.myapplicationflashlight;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MainActivityFlashLight extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = MainActivityFlashLight.class.getCanonicalName();

    private ToggleButton mOnOffToggleButton;
    private Button mbyTimerButton;
    private Spinner mTimerSpinner;

    private Intent mIntentFlashLightService;

    private List<TimerName> mIntegersForTimeOut;

    private long mMillisInFuture;

    public Intent getIntentFlashLightService() {
        if (null == this.mIntentFlashLightService) {
            Log.i(TAG, "mIntentFlashLightService is null");
            this.mIntentFlashLightService = new Intent(getBaseContext(), FlashLightService.class);
        }
        Log.i(TAG, "mIntentFlashLightService return");
        return this.mIntentFlashLightService;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_flash_light);
        Log.i(TAG, "on create main activity");

        this.mMillisInFuture = 10L;
        Log.i(TAG, "mMillisInFuture = 10L");
        this.mOnOffToggleButton = (ToggleButton) findViewById(R.id.onOffFlashLight);
        this.mOnOffToggleButton.setChecked(false);
        this.mOnOffToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startService(getIntentFlashLightService());
                    Log.i(TAG, "start service");

                } else {
                    stopService(getIntentFlashLightService());
                    Log.i(TAG, "stop service");
                }
                mbyTimerButton.setEnabled(!isChecked);
                mTimerSpinner.setEnabled(!isChecked);
            }
        });

        this.mbyTimerButton = (Button) findViewById(R.id.byTimer);
        this.mbyTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCountDownTimer().start();
                Log.i(TAG, "started timer flashlight");
            }
        });

        this.mTimerSpinner = (Spinner) findViewById(R.id.timerSpinner);
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

    private CountDownTimer getCountDownTimer(){
        return new CountDownTimer(this.mMillisInFuture, 1L) {
            int count = 0;

            @Override
            public void onTick(long millisUntilFinished) {
                if (count == 0) {
                    startService(getIntentFlashLightService());
                    mOnOffToggleButton.setEnabled(false);
                    mbyTimerButton.setEnabled(false);
                    mTimerSpinner.setEnabled(false);
                }
                Log.i(TAG, "tick " + ++count);
                Log.i(TAG, "millis Until Finished = " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "finish timer");
                stopService(getIntentFlashLightService());
                mOnOffToggleButton.setEnabled(true);
                mbyTimerButton.setEnabled(true);
                mTimerSpinner.setEnabled(true);
            }
        };
    }
}

class TimerName {

    private String name;
    private Long seconds;

    public TimerName(String name, Long seconds) {
        this.name = name;
        this.seconds = seconds;
    }

    public String getName() {
        return name;
    }

    public Long getSeconds() {
        return seconds;
    }

    public static List<String> getStringNames(List<TimerName> timerNames) {
        List<String> strings = new ArrayList<>();
//        timerNames.forEach(timerName -> strings.add(timerName.getName()));
        for (TimerName timerName : timerNames) {
            strings.add(timerName.getName());
        }
        return strings;
    }

}
