package camera.android.mtsmda.com.myapplicationflashlight.fragment.dp;

/**
 * Created by dminzat on 9/8/2017.
 */

public class Run {

    public static void main(String[] args) {
        Subject subject = new Subject();

        new BinaryObserver(subject);
        new OctalObserver(subject);
        subject.setState(19);
    }

}