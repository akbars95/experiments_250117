package crimeintent.android.mtsmda.com.crimeintent.activity;

import android.support.v4.app.Fragment;

import crimeintent.android.mtsmda.com.crimeintent.fragment.CrimeListFragment;

/**
 * Created by dminzat on 8/1/2017.
 */

public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
