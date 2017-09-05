package camera.android.mtsmda.com.myapplicationflashlight.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import camera.android.mtsmda.com.myapplicationflashlight.R;
import camera.android.mtsmda.com.myapplicationflashlight.common.fragment.MyFragmentWithLogger;

/**
 * Created by dminzat on 9/4/2017.
 */

public class SpinnerFragment extends MyFragmentWithLogger implements OnClickListener {

    private Button mPlusButton;
    private EditText mChangeValueEditText;
    private Button mMinusButton;

    private int currentValue;
    private int topValue;
    private int bottomValue;

    public SpinnerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflateView = inflater.inflate(R.layout.spinner_fragment, container);
        initUI(new View[]{inflateView});
        action();
        return inflateView;
    }

    @Override
    public void initUI(View... views) {
        this.mPlusButton = getUIElementButton(views[0], R.id.plusButton);
        this.mMinusButton = getUIElementButton(views[0], R.id.minusButton);
        this.mChangeValueEditText = getUIElementEditText(views[0], R.id.changeValue);
    }

    @Override
    public void setLayoutId() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plusButton: {
                w("currentValue = " + ++currentValue);
            }
            break;
            case R.id.minusButton: {
                w("currentValue = " + --currentValue);
            }
            break;
        }
    }

    private void action() {
        this.mPlusButton.setOnClickListener(this);
        this.mMinusButton.setOnClickListener(this);
        this.mChangeValueEditText.setText(this.currentValue);
        this.mChangeValueEditText.setEnabled(false);
        this.mChangeValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}