package crimeintent.android.mtsmda.com.crimeintent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import crimeintent.android.mtsmda.com.crimeintent.R;
import crimeintent.android.mtsmda.com.crimeintent.activity.CrimeActivity;
import crimeintent.android.mtsmda.com.crimeintent.activity.CrimePagerActivity;
import crimeintent.android.mtsmda.com.crimeintent.model.Crime;
import crimeintent.android.mtsmda.com.crimeintent.repository.CrimeLab;

/**
 * Created by dminzat on 8/1/2017.
 */

public class CrimeListFragment extends Fragment {

    private static final int REQUEST_CRIME = 1;

    private RecyclerView mCrimeRecyclerView;

    private CrimeAdapter mCrimeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        if(null == mCrimeAdapter){
            mCrimeAdapter = new CrimeAdapter(crimeLab.getCrimes());
            mCrimeRecyclerView.setAdapter(mCrimeAdapter);
        }else{
            mCrimeAdapter.notifyDataSetChanged();
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mIconImageView;

        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            this.mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            this.mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            this.mIconImageView = (ImageView) itemView.findViewById(R.id.imageViewIcon);
            itemView.setOnClickListener(this);
        }

        public void bind(Crime crime) {
            this.mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            this.mIconImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
//            startActivityForResult(CrimeActivity.newIntent(getActivity(), mCrime.getId()), REQUEST_CRIME);
            startActivity(CrimePagerActivity.newIntent(getActivity(), mCrime.getId()));
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CrimeHolder(LayoutInflater.from(getActivity()), parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
            holder.bind(crime);
        }

        @Override
        public int getItemCount() {
            return this.mCrimes.size();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CRIME){

        }
    }
}
