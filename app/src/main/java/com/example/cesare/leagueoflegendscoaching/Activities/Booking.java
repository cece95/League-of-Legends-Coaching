package com.example.cesare.leagueoflegendscoaching.Activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.cesare.leagueoflegendscoaching.Activities.Fragments.ActiveReservations;
import com.example.cesare.leagueoflegendscoaching.Activities.Fragments.PastReservations;
import com.example.cesare.leagueoflegendscoaching.Classes.Components.Adapters.SectionPageAdapter;
import com.example.cesare.leagueoflegendscoaching.R;

public class Booking extends AppCompatActivity{
    private static final String TAG = "Booking";
    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new PastReservations(), "PAST RESERVATIONS");
        adapter.addFragment(new ActiveReservations(), "ACTIVE RESERVATIONS");
        viewPager.setAdapter(adapter);
    }
}
