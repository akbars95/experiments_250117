package crimeintent.android.mtsmda.com.crimeintent.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by dminzat on 7/28/2017.
 */

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        this.mId = UUID.randomUUID();
        this.mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public static Crime build(String title, boolean solved){
        Crime crime = new Crime();
        crime.setSolved(solved);
        crime.setTitle(title);
        return crime;
    }
}
