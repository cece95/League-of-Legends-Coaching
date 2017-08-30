package com.example.cesare.leagueoflegendscoaching.Activities.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cesare.leagueoflegendscoaching.Classes.Components.Adapters.ReservationFrameAdapter;
import com.example.cesare.leagueoflegendscoaching.Classes.Components.ReservationFrame;
import com.example.cesare.leagueoflegendscoaching.R;

import java.util.List;

public class PastReservations extends Fragment {

    private static final String TAG = "PAST RESERVATIONS";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d("CHECK", "past");
        View parent = inflater.inflate(R.layout.past_reservations_fragment, container, false);
        Activity activity = getActivity();
        Log.d("CHECK", "5");
        List<ReservationFrame> reservationFrameList = (List<ReservationFrame>) getArguments().getSerializable("lista");
        if (activity != null){
            Log.d("CHECK", "6");
            ArrayAdapter<ReservationFrame> adapter = new ReservationFrameAdapter(activity, R.layout.reservation_frame, reservationFrameList);
            ListView lv = (ListView) parent.findViewById(R.id.pastList);
            Log.d("CHECK", "7");
            lv.setAdapter(adapter);
            Log.d("CHECK", "8");
        }
        return parent;
    }
}
