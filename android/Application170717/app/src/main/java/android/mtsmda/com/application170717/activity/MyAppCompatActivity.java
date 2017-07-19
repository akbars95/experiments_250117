package android.mtsmda.com.application170717.activity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by dminzat on 7/19/2017.
 */

public class MyAppCompatActivity extends AppCompatActivity {

    protected <T> T findViewById(int id, Class<T> returnType) {
        return (T) super.findViewById(id);
    }

}
