package com.example.cesare.leagueoflegendscoaching.Classes.Components.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.cesare.leagueoflegendscoaching.Activities.Fragments.ActiveReservations;
import com.example.cesare.leagueoflegendscoaching.Activities.Fragments.PastReservations;
import com.example.cesare.leagueoflegendscoaching.Classes.Components.ReservationFrame;
import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.ScheduleParams;
import com.example.cesare.leagueoflegendscoaching.Operations.ScheduleOperation;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

/**
 * Created by cesare on 29/08/2017.
 */

public class SectionPageAdapter extends FragmentPagerAdapter{

    private ArrayList<ReservationFrame> past;
    private ArrayList<ReservationFrame> active;

    public SectionPageAdapter(FragmentManager fm, Activity a, Context c, String type) throws ExecutionException, InterruptedException, JSONException, ParseException {
        super(fm);

        String user = LoggedUser.getIstance(null, null, false).getIgn();
        ScheduleParams params = new ScheduleParams(user, c, type);
        JSONObject json = new ScheduleOperation().execute(params).get();

        if(type == "user") {

            Iterator x = json.keys();
            Date now = new Date();
            ArrayList<ReservationFrame> pastList = new ArrayList<>();
            ArrayList<ReservationFrame> activeList = new ArrayList<>();

            while (x.hasNext()) {
                String date = (String) x.next();
                JSONObject reservationsArray = json.getJSONObject(date);
                Iterator y = reservationsArray.keys();

                while (y.hasNext()) {
                    String key = (String) y.next();
                    ReservationFrame reservation = new ReservationFrame(date, key, reservationsArray.getJSONObject(key), a);
                    if (reservation.getDate().before(now)) {
                        pastList.add(reservation);
                    } else {
                        activeList.add(reservation);
                    }
                }
            }
            this.past = pastList;
            this.active = activeList;
        }
        else if (type == "coachR"){
            Iterator x = json.keys();
            Date now = new Date();
            ArrayList<ReservationFrame> pastList = new ArrayList<>();
            ArrayList<ReservationFrame> activeList = new ArrayList<>();

            while (x.hasNext()) {
                String date = (String) x.next();
                if (date != "status") {
                    JSONObject reservationsArray = json.getJSONObject(date);
                    Iterator y = reservationsArray.keys();

                    while (y.hasNext()) {
                        String key = (String) y.next();
                        if (key != "array") {
                            ReservationFrame reservation = new ReservationFrame(date, key, reservationsArray.getJSONObject(key), a);
                            if (reservation.getDate().before(now)) {
                                pastList.add(reservation);
                            } else {
                                activeList.add(reservation);
                            }
                        }
                    }
                }
                this.past = pastList;
                this.active = activeList;
            }
            Log.d("STATUS", "SectionPageAdapter");
        }
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new ActiveReservations();
            Bundle b = new Bundle();
            b.putSerializable("lista", active);
            fragment.setArguments(b);
        }
        else if (position == 1)
        {
            fragment = new PastReservations();
            Bundle b = new Bundle();
            b.putSerializable("lista", past);
            fragment.setArguments(b);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "ACTIVE RESERVATIONS";
        }
        else if (position == 1)
        {
            title = "PAST RESERVATIONS";
        }
        return title;
    }
}
