package camera.android.mtsmda.com.myapplicationflashlight.fragment.dp;

/**
 * Created by dminzat on 9/8/2017.
 */

public class OctalObserver extends Observer {

    public OctalObserver(Subject subject) {
        this.mSubject = subject;
        this.mSubject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("binary " + Integer.toOctalString(this.mSubject.getState()));
    }
}
