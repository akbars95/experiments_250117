package camera.android.mtsmda.com.myapplicationflashlight.common;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by dminzat on 9/4/2017.
 */

public interface ElementForView {

    <T> T getUIElement(View view, int id, Class<T> tClass);
    Button getUIElementButton(View view, int id);
    ToggleButton getUIElementToggleButton(View view, int id);
    TextView getUIElementTextView(View view, int id);
    Spinner getUIElementSpinner(View view, int id);
    EditText getUIElementEditText(View view, int id);

}