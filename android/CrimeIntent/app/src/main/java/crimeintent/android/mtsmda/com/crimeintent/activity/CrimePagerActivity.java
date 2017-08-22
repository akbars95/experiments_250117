package crimeintent.android.mtsmda.com.crimeintent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import crimeintent.android.mtsmda.com.crimeintent.R;
import crimeintent.android.mtsmda.com.crimeintent.fragment.CrimeFragment;
import crimeintent.android.mtsmda.com.crimeintent.model.Crime;
import crimeintent.android.mtsmda.com.crimeintent.repository.CrimeLab;

/**
 * Created by dminzat on 8/8/2017.
 */

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "com.mtsmda.android.crime_pager.crime_id";

    private ViewPager mViewPager;
    private List<Crime> mCrimes;

    public static Intent newIntent(Context context, UUID crimeId){
        Intent intent = new Intent(context, CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        UUID uuidCrimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        this.mViewPager = (ViewPager) findViewById(R.id.viewPager);
        this.mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return CrimeFragment.newInstance(mCrimes.get(position).getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        for (int i = 0; i < mCrimes.size(); i++){
            if(mCrimes.get(i).getId().equals(uuidCrimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }
}
