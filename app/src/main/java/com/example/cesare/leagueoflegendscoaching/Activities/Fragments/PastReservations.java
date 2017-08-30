package com.example.cesare.leagueoflegendscoaching.Activities.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.example.cesare.leagueoflegendscoaching.Classes.Components.Adapters.ReservationFrameAdapter;
import com.example.cesare.leagueoflegendscoaching.Classes.Components.ReservationFrame;
import com.example.cesare.leagueoflegendscoaching.R;

import java.util.List;

public class PastReservations extends ListFragment {

    private static final String TAG = "PAST RESERVATIONS";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.past_reservations_fragment, container, false);
        Activity activity = getActivity();
        List<ReservationFrame> reservationFrameList = (List<ReservationFrame>) getArguments().getSerializable("lista");
        if (activity != null){
            ListAdapter adapter = new ReservationFrameAdapter(activity, R.layout.reservation_frame, reservationFrameList);
            setListAdapter(adapter);
        }


        return parent;
    }
}
