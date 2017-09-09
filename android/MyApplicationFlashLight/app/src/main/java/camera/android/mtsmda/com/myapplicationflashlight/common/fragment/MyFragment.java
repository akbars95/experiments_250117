package camera.android.mtsmda.com.myapplicationflashlight.common.fragment;

import android.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import camera.android.mtsmda.com.myapplicationflashlight.common.ElementForView;

/**
 * Created by dminzat on 9/4/2017.
 */

public class MyFragment extends Fragment implements ElementForView {

    @Override
    public <T> T getUIElement(View view, int id, Class<T> tClass) {
        return (T)view.findViewById(id);
    }

    @Override
    public Button getUIElementButton(View view, int id) {
        return getUIElement(view, id, Button.class);
    }

    @Override
    public ToggleButton getUIElementToggleButton(View view, int id) {
        return getUIElement(view, id, ToggleButton.class);
    }

    @Override
    public TextView getUIElementTextView(View view, int id) {
        return getUIElement(view, id, TextView.class);
    }

    @Override
    public Spinner getUIElementSpinner(View view, int id) {
        return getUIElement(view, id, Spinner.class);
    }

    @Override
    public EditText getUIElementEditText(View view, int id) {
        return getUIElement(view, id, EditText.class);
    }
}