package crimeintent.android.mtsmda.com.flashlightapplication;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private ToggleButton mTurnOnOffToggleButton;
    private Camera mCamera;
    private boolean isOpenedCamera = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mTurnOnOffToggleButton = (ToggleButton) findViewById(R.id.onOffFlashlight);
        if (!this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
//            Toast.makeText(this, R.string.error_camera, Toast.LENGTH_SHORT);
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Error");
            alertDialog.setMessage(getResources().getString(R.string.error_camera));
            alertDialog.setButton(0, "OK", new DialogInterface.OnClickListener(){
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
                cameraOnOff(isChecked);
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
    }

    private void cameraOnOff(boolean isEnable) {
        if(!isOpenedCamera){
            mCamera = Camera.open();
            isOpenedCamera = true;
        }
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
        }
    }

}
