package crimeintent.android.mtsmda.com.crimeintent.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import crimeintent.android.mtsmda.com.crimeintent.R;

/**
 * Created by dminzat on 8/1/2017.
 */

public abstract class SingleFragmentActivity extends MyAppCompatActivity {

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_crime);
        if(null == fragment){
            fragment = createFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_crime, fragment).commit();
        }
    }
}
