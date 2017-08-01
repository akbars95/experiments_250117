package crimeintent.android.mtsmda.com.crimeintent.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import crimeintent.android.mtsmda.com.crimeintent.R;
import crimeintent.android.mtsmda.com.crimeintent.model.Crime;

/**
 * Created by dminzat on 7/31/2017.
 */

public class CrimeFragment extends Fragment {

    private Crime mCrime;

    private EditText mTitleEditText;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mCrime = new Crime();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime, container, false);
        this.mTitleEditText = (EditText) view.findViewById(R.id.crime_title);
        this.mTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        this.mDateButton = (Button) view.findViewById(R.id.crime_date);
        this.mDateButton.setText(mCrime.getDate().toString());
        this.mDateButton.setEnabled(false);

        this.mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
        this.mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });

        return view;
    }
}
