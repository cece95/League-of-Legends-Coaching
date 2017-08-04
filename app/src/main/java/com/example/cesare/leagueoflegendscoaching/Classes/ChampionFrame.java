package com.example.cesare.leagueoflegendscoaching.Classes;

import android.view.LayoutInflater;
import android.view.View;

import com.example.cesare.leagueoflegendscoaching.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cesare on 03/08/2017.
 */

public class ChampionFrame {
    String name;
    int id;
    int n_games;

    public ChampionFrame(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.n_games = json.getInt("n_games");
        this.name = ChampionsMap.idToChamp.get(this.id);
    }

    public View createFrame(LayoutInflater inflater){
        View frame = inflater.inflate(R.layout.champion_frame, null);

        return frame;
    }
}
