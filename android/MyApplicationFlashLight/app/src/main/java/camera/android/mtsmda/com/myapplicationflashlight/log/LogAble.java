package camera.android.mtsmda.com.myapplicationflashlight.log;

/**
 * Created by dminzat on 9/1/2017.
 */

public interface LogAble {

    void d(String message, Throwable throwable);
    void d(String message);
    void w(String message, Throwable throwable);
    void w(String message);

}