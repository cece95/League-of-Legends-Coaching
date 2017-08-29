package com.example.cesare.leagueoflegendscoaching.Activities.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.cesare.leagueoflegendscoaching.Classes.Components.ReservationFrame;
import com.example.cesare.leagueoflegendscoaching.R;

import java.util.List;

public class PastReservations extends Fragment {

    private static final String TAG = "PAST RESERVATIONS";

    @// TODO: 29/08/2017 Ottenere la lista prenotazioni passate
    @// TODO: 29/08/2017 reservation_frame.xml

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.past_reservations_fragment, container, false);
        Activity activity = getActivity();
        List<ReservationFrame> reservationFrameList = null;
        if (activity != null){
            ArrayAdapter<ReservationFrame> adapter = new ArrayAdapter<ReservationFrame>(activity, R.layout.reservation_frame, reservationFrameList);
            ListView lv = (ListView) parent.findViewById(R.id.pastList);
            lv.setAdapter(adapter);
        }


        return parent;
    }
}
