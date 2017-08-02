package crimeintent.android.mtsmda.com.flashlightapplication;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private ToggleButton mTurnOnOffToggleButton;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mTurnOnOffToggleButton = (ToggleButton) findViewById(R.id.onOffFlashlight);
        if (!this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, R.string.error_camera, Toast.LENGTH_SHORT);
            return;
        }
        this.mTurnOnOffToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cameraOnOff(isChecked);
            }
        });
    }

    private void cameraOnOff(boolean isEnable) {
        mCamera = Camera.open();
        Camera.Parameters parameters = mCamera.getParameters();
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
//            mCamera.release();
        }
    }

}
