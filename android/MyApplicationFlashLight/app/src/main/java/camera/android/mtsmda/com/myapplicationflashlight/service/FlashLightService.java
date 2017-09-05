package camera.android.mtsmda.com.myapplicationflashlight.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import camera.android.mtsmda.com.myapplicationflashlight.R;
import camera.android.mtsmda.com.myapplicationflashlight.log.L;

/**
 * Created by dminzat on 8/18/2017.
 */

public class FlashLightService extends Service {

    private Camera mCamera;
    private Camera.Parameters parameters;
    private L l;

    public FlashLightService() {
        this.l = new L(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        l.w("init service");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, getString(R.string.flashlight_started), Toast.LENGTH_SHORT).show();
        l.w("Service started");
        initCamera();
        l.w("Camera init");
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters(parameters);
        mCamera.startPreview();
        l.w("Camera started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, getString(R.string.flashlight_stopped), Toast.LENGTH_SHORT).show();
        l.w("Service stopped");
        initCamera();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        mCamera.setParameters(parameters);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
        parameters = null;
        l.w("Camera stopped");
    }

    private void initCamera() {
        l.w("null == mCamera = " + (null == mCamera));
        if (null == mCamera) {
            try {
                mCamera = Camera.open();
            } catch (Exception e) {
                l.w("Exception init camera - " + e.getClass().getCanonicalName() + ", " + e.getMessage());
            }
        }
        l.w("null == parameters = " + (null == parameters));
        if (null == parameters) {
            parameters = mCamera.getParameters();
        }
    }

}
