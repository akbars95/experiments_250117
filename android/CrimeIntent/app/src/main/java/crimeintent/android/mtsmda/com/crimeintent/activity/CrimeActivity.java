package crimeintent.android.mtsmda.com.crimeintent.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

import crimeintent.android.mtsmda.com.crimeintent.R;
import crimeintent.android.mtsmda.com.crimeintent.fragment.CrimeFragment;

public class CrimeActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID = "com.mtsmda.android.crime_id";

    public static Intent newIntent(Context context, UUID crimeId) {
        Intent intent = new Intent(context, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return CrimeFragment.newInstance((UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID));
    }


}
