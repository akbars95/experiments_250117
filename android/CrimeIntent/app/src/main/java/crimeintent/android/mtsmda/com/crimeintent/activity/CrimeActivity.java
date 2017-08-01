package crimeintent.android.mtsmda.com.crimeintent.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import crimeintent.android.mtsmda.com.crimeintent.R;
import crimeintent.android.mtsmda.com.crimeintent.fragment.CrimeFragment;

public class CrimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.fragment_crime);
        if(null == fragment){
            fragment = new CrimeFragment();
            supportFragmentManager.beginTransaction().add(R.id.fragment_crime, fragment).commit();
        }
    }
}
