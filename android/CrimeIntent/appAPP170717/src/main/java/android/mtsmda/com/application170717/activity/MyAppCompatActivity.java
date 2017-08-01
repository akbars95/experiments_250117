package android.mtsmda.com.application170717.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by dminzat on 7/19/2017.
 */

public class MyAppCompatActivity extends AppCompatActivity {

    protected <T> T findViewById(int id, Class<T> returnType) {
        return (T) super.findViewById(id);
    }

    protected Button findViewByIdButton(int id) {
        return findViewById(id, Button.class);
    }

    protected TextView findViewByIdTextView(int id) {
        return findViewById(id, TextView.class);
    }

    protected ImageButton findViewByIdImageButton(int id) {
        return findViewById(id, ImageButton.class);
    }

}
