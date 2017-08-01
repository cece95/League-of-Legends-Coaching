package com.example.cesare.leagueoflegendscoaching.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cesare.leagueoflegendscoaching.R;
import com.example.cesare.leagueoflegendscoaching.Types.Language;
import com.example.cesare.leagueoflegendscoaching.Types.Role;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;

/**
 * Created by asus on 12/07/2017.
 */

public class CoachFrame {
    String name;
    int elo;
    Role role1;
    Role role2;
    int cost;
    HashSet<Language> languages;


    public CoachFrame(JSONObject json) throws JSONException {
        this.name = json.getString("ign");
        this.elo = json.getInt("elo");
        this.role1 = Role.valueOf((String) json.get("role1"));
        this.role2 = Role.valueOf((String) json.get("role2"));
        this.cost = json.getInt("cost");
        this.languages = getLanguages(json);
    }

    public View createFrame(Context context){
        //frame principale
        View frame = LayoutInflater.from(context).inflate(R.layout.coach_frame, null);

        //eloImage
        ImageView eloImageView = (ImageView) frame.findViewById(R.id.eloImage);
        setEloImage(context, eloImageView);

        //nameCost
        TextView name = (TextView) frame.findViewById(R.id.name);
        name.setText(this.name);
        TextView cost = (TextView) frame.findViewById(R.id.cost);
        cost.setText("Costo: "+this.cost+"€");

        //rolesDetails
        TextView roles = (TextView) frame.findViewById(R.id.roles);
        String rolesText;
        if(this.role2 == Role.None){
            rolesText = "Role: "+String.valueOf(this.role1);
        }
        else{
            rolesText = "Roles: "+this.role1+"/"+this.role2;
        }
        roles.setText(rolesText);
        Button details = (Button) frame.findViewById(R.id.details);
        //set onclick listener

        return frame;
    }

    private void setEloImage(Context context, ImageView img) {
        switch (this.elo) {
            case 0 : img.setBackgroundResource(R.mipmap.badge_bronze);
                break;
            case 1 : img.setBackgroundResource(R.mipmap.badge_silver);
                break;
            case 2 : img.setBackgroundResource(R.mipmap.badge_gold);
                break;
            case 3 : img.setBackgroundResource(R.mipmap.badge_platinum);
                break;
            case 4 : img.setBackgroundResource(R.mipmap.badge_diamond);
                break;
            case 5 : img.setBackgroundResource(R.mipmap.badge_master);
                break;
            case 6 : img.setBackgroundResource(R.mipmap.badge_challenger);
                break;
        }
    }

    private HashSet<Language> getLanguages(JSONObject json) throws JSONException {
        HashSet<Language> res = new HashSet<>();

        JSONArray arrJson = json.getJSONArray("languages");
        for(int i = 0; i < arrJson.length(); i++)
            res.add(Language.valueOf(arrJson.getString(i)));

        return res;
    }
}
