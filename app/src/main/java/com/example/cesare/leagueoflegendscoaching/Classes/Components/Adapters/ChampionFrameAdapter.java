package com.example.cesare.leagueoflegendscoaching.Classes.Components.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.cesare.leagueoflegendscoaching.Classes.Components.ChampionFrame;

import java.util.List;

/**
 * Created by cesare on 04/08/2017.
 */

public class ChampionFrameAdapter extends ArrayAdapter<ChampionFrame> {

    private LayoutInflater frameInflater;

    public ChampionFrameAdapter(@NonNull Context context, @LayoutRes int textViewResourceId, @NonNull List<ChampionFrame> frameList) {
        super(context, textViewResourceId, frameList);
        frameInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ChampionFrame championFrame = getItem(position);
        View view = championFrame.createFrame(frameInflater);

        return view;
    }
}

