package com.example.cesare.leagueoflegendscoaching.Classes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

        //championImage
        ImageView championImage = (ImageView) frame.findViewById(R.id.championImage);
        String cName = clean(this.name);
        int imgId = frame.getResources().getIdentifier(cName, "mipmap", frame.getContext().getPackageName());
        Log.d("ImgId", String.valueOf(imgId));
        championImage.setBackgroundResource(imgId);

        //championName
        TextView championName = (TextView) frame.findViewById(R.id.championName);
        championName.setText(this.name);

        //n_games
        TextView nGames = (TextView) frame.findViewById(R.id.n_games);
        nGames.setText(Integer.toString(this.n_games));

        return frame;
    }

    private String clean(String champion){
        String temp = champion.replace(" ", "");
        temp = temp.replace("'", "");
        String res = temp.toLowerCase();
        return  res;
    }
}
