package com.example.cesare.leagueoflegendscoaching.Activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.cesare.leagueoflegendscoaching.Activities.Fragments.ActiveReservations;
import com.example.cesare.leagueoflegendscoaching.Activities.Fragments.PastReservations;
import com.example.cesare.leagueoflegendscoaching.Classes.Components.Adapters.SectionPageAdapter;
import com.example.cesare.leagueoflegendscoaching.Classes.Components.ReservationFrame;
import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.ScheduleParams;
import com.example.cesare.leagueoflegendscoaching.Operations.ScheduleOperation;
import com.example.cesare.leagueoflegendscoaching.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Booking2 extends AppCompatActivity{
    private static final String TAG = "Booking2";
    private SectionPageAdapter mSectionPageAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);

        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);

        ScheduleParams params = new ScheduleParams(LoggedUser.getIstance(null, null, false).getIgn(), this, "user");
        JSONObject reservations;
        List<ReservationFrame> past = new ArrayList<>();
        List<ReservationFrame> active = new ArrayList<>();
        Date now = new Date();

        try {
            reservations = new ScheduleOperation().execute(params).get();
            Iterator x = reservations.keys();
            while (x.hasNext()){
                String date = (String) x.next();
                ReservationFrame reservation = new ReservationFrame(date, reservations.getJSONObject(date));
                if (reservation.getDate().before(now)){
                    past.add(reservation);
                }
                else{
                    active.add(reservation);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("CHECK", "1");
        setupViewPager(mViewPager, past, active);
    }

    private void setupViewPager(ViewPager viewPager, List<ReservationFrame> past, List<ReservationFrame> active){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());

        Bundle pastB = new Bundle();
        pastB.putSerializable("lista", (Serializable) past);
        Bundle activeB = new Bundle();
        activeB.putSerializable("lista", (Serializable) active);

        Log.d("CHECK", "2");

        PastReservations pastR = new PastReservations();
        pastR.setArguments(pastB);
        ActiveReservations activeR = new ActiveReservations();
        activeR.setArguments(activeB);

        Log.d("CHECK", "3");

        adapter.addFragment(pastR,"PAST RESERVATIONS");
        adapter.addFragment(activeR, "ACTIVE RESERVATIONS");

        adapter.print();

        viewPager.setAdapter(adapter);

        Log.d("CHECK", "4");
    }
}
