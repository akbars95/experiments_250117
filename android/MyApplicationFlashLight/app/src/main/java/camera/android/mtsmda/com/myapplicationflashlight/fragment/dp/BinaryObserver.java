package camera.android.mtsmda.com.myapplicationflashlight.fragment.dp;

/**
 * Created by dminzat on 9/8/2017.
 */

public class BinaryObserver extends Observer {

    public BinaryObserver(Subject subject) {
        this.mSubject = subject;
        this.mSubject.attach(this);
    }

    @Override
    public void update() {
        System.out.println("binary " + Integer.toBinaryString(this.mSubject.getState()));
    }
}
