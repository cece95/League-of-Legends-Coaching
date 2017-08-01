package com.example.cesare.leagueoflegendscoaching.Classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.cesare.leagueoflegendscoaching.R;
import com.example.cesare.leagueoflegendscoaching.Types.Language;
import com.example.cesare.leagueoflegendscoaching.Types.Role;

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
    Bitmap eloImage;


    public CoachFrame(JSONObject json) throws JSONException {
        this.name = json.getString("nameCoach");
        this.elo = json.getInt("elo");
        this.role1 = (Role) json.get("role1");
        this.role2 = (Role) json.get("role2");
        this.cost = json.getInt("cost");
        this.languages = (HashSet<Language>) json.get("languages");
    }

    public LinearLayout createFrame(Context context){
        //frame
        LinearLayout frame = new LinearLayout(context);
        frame.setOrientation(LinearLayout.HORIZONTAL);

        //image
        ImageView eloSymbol = new ImageView(context);
        eloSymbol.setImageBitmap(eloImage);
        frame.addView(eloSymbol);

        //info
        LinearLayout infoBox = new LinearLayout(context);
        infoBox.setOrientation(LinearLayout.VERTICAL);
        frame.addView(infoBox);

        //name + cost
        LinearLayout nameCost = new LinearLayout(context);
        nameCost.setOrientation(LinearLayout.HORIZONTAL);

        TextView name = new TextView(context);
        name.setText(this.name);

        TextView cost = new TextView(context);
        cost.setText(this.cost+" €");

        nameCost.addView(name);
        nameCost.addView(cost);

        infoBox.addView(nameCost);

        //role + detalils
        LinearLayout roleDetails = new LinearLayout(context);
        roleDetails.setOrientation(LinearLayout.HORIZONTAL);

        TextView role = new TextView(context);
        role.setText(role1+"/"+role2);

        Button details = new Button(context);
        details.setText(R.string.details);

        roleDetails.addView(role);
        roleDetails.addView(details);

        return frame;
    }

}
