package camera.android.mtsmda.com.myapplicationflashlight.fragment;

import android.content.Context;
import android.os.Build;
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
import android.widget.Toast;

import camera.android.mtsmda.com.myapplicationflashlight.R;
import camera.android.mtsmda.com.myapplicationflashlight.common.Helper;
import camera.android.mtsmda.com.myapplicationflashlight.common.fragment.MyFragmentWithLogger;

/**
 * Created by dminzat on 9/4/2017.
 */

public class SpinnerFragment extends MyFragmentWithLogger implements OnClickListener {

    private static final int MORE = 1;
    private static final int LESS = -1;
    private static final int OK = 0;

    private Button mPlusButton;
    private EditText mChangeValueEditText;
    private Button mMinusButton;

    private Integer currentValue;
    private int topValue;
    private int bottomValue;
    private boolean isChanged;

    public SpinnerFragment() {
    }

    public SpinnerFragment(int currentValue, int topValue, int bottomValue) {
        super();
        this.currentValue = currentValue;
        this.topValue = topValue;
        this.bottomValue = bottomValue;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        w("init spinner fragment");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflateView = inflater.inflate(R.layout.spinner_fragment, container, false);
        initUI(new View[]{inflateView});
        action();
        w("return view from fragment");
        return inflateView;
    }

    @Override
    public void initUI(View... views) {
        w("views count is  " + views.length);
        w("null == views[0]" + (null == views[0]));
        this.mPlusButton = getUIElementButton(views[0], R.id.plusButton);
        this.mMinusButton = getUIElementButton(views[0], R.id.minusButton);
        this.mChangeValueEditText = getUIElementEditText(views[0], R.id.changeValue);
        w("this.mChangeValueEditText is null " + (null == this.mChangeValueEditText));
        w("init spinner fragment UI elements");
    }

    @Override
    public void setLayoutId() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.plusButton: {
                isChanged = true;
                w("onclick button + isChanged = " + isChanged);
                w("+ button");
                w("currentValue = " + ++currentValue);
                switch (controlHMS()) {
                    case MORE: {
                        Helper.showToastShort(Build.VERSION.SDK_INT > Build.VERSION_CODES.M ? getContext() : getActivity(), getString(R.string.spinner_button_fragment_error_more, this.currentValue));
                    }
                    break;
                    case LESS: {
                        Helper.showToastShort(Build.VERSION.SDK_INT > Build.VERSION_CODES.M ? getContext() : getActivity(), getString(R.string.spinner_button_fragment_error_less, this.currentValue));
                    }
                    break;
                }
            }
            break;
            case R.id.minusButton: {
                isChanged = true;
                w("onclick button - isChanged = " + isChanged);
                w("- button");
                w("currentValue = " + --currentValue);
                switch (controlHMS()) {
                    case MORE: {
                        Helper.showToastShort(Build.VERSION.SDK_INT > Build.VERSION_CODES.M ? getContext() : getActivity(), getString(R.string.spinner_button_fragment_error_more, this.currentValue));
                    }
                    break;
                    case LESS: {
                        Helper.showToastShort(Build.VERSION.SDK_INT > Build.VERSION_CODES.M ? getContext() : getActivity(), getString(R.string.spinner_button_fragment_error_less, this.currentValue));
                    }
                    break;
                }
            }
            break;
        }
        this.mChangeValueEditText.setText(this.currentValue.toString());
    }

    public boolean isChanged() {
        return isChanged;
    }

    public boolean isCurrentValueNotEqualNull() {
        return !isCurrentValueEqualNull();
    }

    public boolean isCurrentValueEqualNull() {
        return currentValue == null || currentValue == 0;
    }

    public Integer getCurrentValue() {
        return currentValue;
    }

    private int controlHMS() {
        if (currentValue > topValue) {
            currentValue = topValue;
            return MORE;
        }
        if (currentValue < bottomValue) {
            currentValue = bottomValue;
            return LESS;
        }
        return OK;
    }

    private void action() {
        this.mPlusButton.setOnClickListener(this);
        this.mMinusButton.setOnClickListener(this);
        w("this.currentValue = " + this.currentValue);
        this.mChangeValueEditText.setText((null == this.currentValue) ? "0" : this.currentValue.toString());
//        this.mChangeValueEditText.setEnabled(false);
        w("initial action for elements");

        this.mChangeValueEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                w("current text is " + s);
                if (null == s || 0 == s.length()) {
                    currentValue = 0;
                } else {
                    currentValue = Integer.parseInt(s.toString());
                    isChanged = true;
                    w("text change = " + isChanged);
                }
                controlHMS();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.mChangeValueEditText.setOnFocusChangeListener((v, hasFocus) -> {
            w("has focus = " + hasFocus);
            w("id = " + v.getId());
            mChangeValueEditText.setText(currentValue.toString());
            w("wrote " + currentValue);
            w("wrote mChangeValueEditText = " + mChangeValueEditText.getText().toString());
        });

    }

}