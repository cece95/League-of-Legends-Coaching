package com.example.cesare.leagueoflegendscoaching.Classes.Components.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.cesare.leagueoflegendscoaching.Classes.Components.ReservationFrame;
import com.example.cesare.leagueoflegendscoaching.R;

import java.util.List;

/**
 * Created by cesare on 29/08/2017.
 */

public class ReservationFrameAdapter extends ArrayAdapter<ReservationFrame> {
    private LayoutInflater frameInflater;

    public ReservationFrameAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<ReservationFrame> objects) {
        super(context, resource, objects);
        frameInflater = LayoutInflater.from(context);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ReservationFrame reservationFrame = getItem(position);
        View frame = frameInflater.inflate(R.layout.reservation_frame, null);
        reservationFrame.createFrame(frame);

        return frame;
    }
}
