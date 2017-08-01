package crimeintent.android.mtsmda.com.crimeintent.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import crimeintent.android.mtsmda.com.crimeintent.model.Crime;

/**
 * Created by dminzat on 8/1/2017.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;

    private CrimeLab(Context context) {
        this.mCrimes = new ArrayList<>();
        for (int i = 0; i < 100; i++){
            Crime crime = new Crime();
            crime.setSolved(i % 2 == 0);
            crime.setTitle("Crime #" + i);
            crime.setDate(new Date());
            crime.setId(UUID.randomUUID());
        }
    }

    public static CrimeLab get(Context context) {
        if (null == sCrimeLab) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public List<Crime> getCrimes(){
        return this.mCrimes;
    }

    public Crime getCrime(UUID uuid){
        for (Crime currentCrime : this.mCrimes){
            if(currentCrime.getId().equals(uuid)){
                return currentCrime;
            }
        }
        return null;
    }

}
