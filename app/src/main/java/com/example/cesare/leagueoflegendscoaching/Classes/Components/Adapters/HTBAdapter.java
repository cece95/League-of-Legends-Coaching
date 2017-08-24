package com.example.cesare.leagueoflegendscoaching.Classes.Components.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.cesare.leagueoflegendscoaching.Classes.Components.CoachFrame;
import com.example.cesare.leagueoflegendscoaching.Classes.Components.HourToggleButton;
import com.example.cesare.leagueoflegendscoaching.R;

import java.util.List;

/**
 * Created by asus on 24/08/2017.
 */

public class HTBAdapter extends ArrayAdapter<HourToggleButton> {
    private LayoutInflater frameInflater;

    public HTBAdapter(@NonNull Context context, @LayoutRes int textViewResourceId, @NonNull List<HourToggleButton> frameList) {
        super(context, textViewResourceId, frameList);
        frameInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        HourToggleButton htbFrame = getItem(position);
        View frame = frameInflater.inflate(R.layout.hour_toggle_button, null);
        htbFrame.create(frame);

        return frame;
    }
}
