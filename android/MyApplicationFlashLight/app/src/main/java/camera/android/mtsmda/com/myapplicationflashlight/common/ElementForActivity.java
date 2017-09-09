package camera.android.mtsmda.com.myapplicationflashlight.common;

import android.support.v7.widget.LinearLayoutCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by dminzat on 9/4/2017.
 */

public interface ElementForActivity {

    <T> T getUIElement(int id, Class<T> tClass);
    Button getUIElementButton(int id);
    ToggleButton getUIElementToggleButton(int id);
    TextView getUIElementTextView(int id);
    Spinner getUIElementSpinner(int id);
    EditText getUIElementEditText(int id);
    LinearLayoutCompat getUIElementLinearLayoutCompat(int id);

}