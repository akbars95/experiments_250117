package camera.android.mtsmda.com.myapplicationflashlight.fragment.dp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dminzat on 9/8/2017.
 */

public class Subject {

    private List<Observer> mObservers = new ArrayList<>();
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void attach(Observer observer){
        mObservers.add(observer);
    }

    public void notifyAllObservers(){
        for (Observer observer : mObservers){
            observer.update();
        }
    }

}