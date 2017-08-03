package com.example.cesare.leagueoflegendscoaching.Classes;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by cesare on 03/08/2017.
 */

public class FrameAdapter extends ArrayAdapter<CoachFrame> {

    private LayoutInflater frameInflater;
    private static  Context context;

    public FrameAdapter(@NonNull Context context, @LayoutRes int textViewResourceId, @NonNull List<CoachFrame> frameList) {
        super(context, textViewResourceId, frameList);
        frameInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CoachFrame coachFrame = getItem(position);
        View view = coachFrame.createFrame(frameInflater);

        return view;
    }
}
