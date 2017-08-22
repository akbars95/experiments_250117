package camera.android.mtsmda.com.myapplicationflashlight;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by dminzat on 8/18/2017.
 */

public class FlashLightService extends Service {

    private static final String TAG = MainActivityFlashLight.class.getCanonicalName();

    private Camera mCamera;
    private Camera.Parameters parameters;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "init service");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, getString(R.string.flashlight_started), Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Service started");
        initCamera();
        Log.i(TAG, "Camera init");
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters(parameters);
        mCamera.startPreview();
        Log.i(TAG, "Camera started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, getString(R.string.flashlight_stopped), Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Service stopped");
        initCamera();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        mCamera.setParameters(parameters);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
        parameters = null;
        Log.i(TAG, "Camera stopped");
    }

    private void initCamera() {
        Log.i(TAG, "null == mCamera = " + (null == mCamera));
        if (null == mCamera) {
            try {
                mCamera = Camera.open();
            } catch (Exception e) {
                Log.i(TAG, "Exception init camera - " + e.getClass().getCanonicalName() + ", " + e.getMessage());
            }
        }
        Log.i(TAG, "null == parameters = " + (null == parameters));
        if (null == parameters) {
            parameters = mCamera.getParameters();
        }
    }

}
