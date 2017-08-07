package crimeintent.android.mtsmda.com.flashlightapplication;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private static final String LOGGER = MainActivity.class.getCanonicalName();
    private static final String IS_RUN_FLASHLIGHT = "I_R_F";

    private ToggleButton mTurnOnOffToggleButton;
    private Camera mCamera;
    private boolean isOpenedCamera = false;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_RUN_FLASHLIGHT, this.isOpenedCamera);
        Log.i(LOGGER, "onSaveInstanceState: IS_RUN_FLASHLIGHT = " + outState.getBoolean(IS_RUN_FLASHLIGHT));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOGGER, "onPause: isOpenedCamera = " + isOpenedCamera);
        if (this.mCamera == null) {
            mCamera = Camera.open();
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (this.isOpenedCamera) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameters);
            mCamera.startPreview();

        } else {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
            mCamera.stopPreview();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOGGER, "onCreate: isOpenedCamera = " + isOpenedCamera);
        this.mTurnOnOffToggleButton = (ToggleButton) findViewById(R.id.onOffFlashlight);
        if (null != savedInstanceState) {
            this.isOpenedCamera = savedInstanceState.getBoolean(IS_RUN_FLASHLIGHT);
        }
        Log.i(LOGGER, "onCreate: after check saved instance state, isOpenedCamera = " + isOpenedCamera);
        if (this.mCamera == null) {
            mCamera = Camera.open();
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (this.isOpenedCamera) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            mCamera.setParameters(parameters);
            mCamera.startPreview();

        } else {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
            mCamera.stopPreview();
        }
        this.mTurnOnOffToggleButton.setChecked(isOpenedCamera);
        Log.i(LOGGER, "onCreate: this phone support camera flash - " + (this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)));
        if (!this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
//            Toast.makeText(this, R.string.error_camera, Toast.LENGTH_SHORT);
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage(getResources().getString(R.string.error_camera));
            alertDialog.setButton(0, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialog.show();
            return;
        }
        this.mTurnOnOffToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.this.isOpenedCamera = isChecked;
                Log.i(LOGGER, "listener toggle button: isOpenedCamera = " + isOpenedCamera);
                if (MainActivity.this.mCamera == null) {
                    mCamera = Camera.open();
                }
                Camera.Parameters parameters = mCamera.getParameters();
                if (MainActivity.this.isOpenedCamera) {
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    mCamera.setParameters(parameters);
                    mCamera.startPreview();

                } else {
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    mCamera.setParameters(parameters);
                    mCamera.stopPreview();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != mCamera) {
            mCamera.release();
            mCamera = null;
        }
        Log.i(LOGGER, "onStop: isOpenedCamera = " + isOpenedCamera);
    }

    private void cameraOnOff(boolean isEnable) {
        Camera.Parameters parameters = null;
        if (null == this.mCamera) {

        }
        if (isEnable) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        } else {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        }
        mCamera.setParameters(parameters);
        if (isEnable) {
            mCamera.startPreview();
        } else {
            mCamera.stopPreview();
        }
    }

}
