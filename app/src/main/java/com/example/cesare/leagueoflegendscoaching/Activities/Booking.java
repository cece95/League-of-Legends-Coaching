package com.example.cesare.leagueoflegendscoaching.Activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.cesare.leagueoflegendscoaching.Classes.Components.Adapters.SectionPageAdapter;
import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.ShakeDetector;
import com.example.cesare.leagueoflegendscoaching.R;

import org.json.JSONException;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

public class Booking extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    SectionPageAdapter viewPagerAdapter;
    private ShakeDetector shaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        shaker = new ShakeDetector(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        try {
            viewPagerAdapter = new SectionPageAdapter(getSupportFragmentManager(), this, this, "user");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
