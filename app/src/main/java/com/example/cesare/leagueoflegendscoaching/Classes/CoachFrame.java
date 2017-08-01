package com.example.cesare.leagueoflegendscoaching.Classes;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
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

    public LinearLayout createFrame(Context context){
        //frame
        LinearLayout frame = new LinearLayout(context);
        frame.setOrientation(LinearLayout.HORIZONTAL);
        frame.setWeightSum(3);
        LayoutParams params = (LayoutParams) frame.getLayoutParams();
        params.width = LayoutParams.MATCH_PARENT;
        params.height = LayoutParams.WRAP_CONTENT;
        frame.setLayoutParams(params);

        // Elo Image Layout
        LinearLayout imageLayout = new LinearLayout(context);
        LayoutParams imageParams = (LayoutParams) imageLayout.getLayoutParams();
        imageParams.weight = 1;
        imageParams.height = LayoutParams.WRAP_CONTENT;
        imageParams.width = 0;
        // Elo Image
        ImageView eloSymbol = getEloImage();

        imageLayout.setLayoutParams(imageParams);
        imageLayout.addView(eloSymbol);

        //info
        LinearLayout infoBox = new LinearLayout(context);
        infoBox.setWeightSum(2);
        infoBox.setOrientation(LinearLayout.VERTICAL);
        LayoutParams infoBoxParams = (LayoutParams) infoBox.getLayoutParams();
        infoBoxParams.weight = 2;
        infoBoxParams.width = 0;
        infoBoxParams.height = LayoutParams.WRAP_CONTENT;
        infoBox.setLayoutParams(infoBoxParams);
        frame.addView(infoBox);

        //name + cost
        LinearLayout nameCost = new LinearLayout(context);
        nameCost.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams nameCostParams = (LayoutParams) nameCost.getLayoutParams();
        nameCostParams.weight = 1;
        nameCostParams.width = LayoutParams.WRAP_CONTENT;
        nameCostParams.height = 0;
        nameCost.setLayoutParams(nameCostParams);

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
        LayoutParams roleDetailsParams =  (LayoutParams) roleDetails.getLayoutParams();
        roleDetailsParams.weight = 1;
        roleDetailsParams.width = LayoutParams.WRAP_CONTENT;
        roleDetailsParams.height = 0;
        roleDetails.setLayoutParams(roleDetailsParams);

        TextView role = new TextView(context);
        role.setText(role1+"/"+role2);

        Button details = new Button(context);
        details.setText(R.string.details);

        roleDetails.addView(role);
        roleDetails.addView(details);

        return frame;
    }

    private ImageView getEloImage() {
        ImageView img = null;
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
        return img;
    }

    private HashSet<Language> getLanguages(JSONObject json) throws JSONException {
        HashSet<Language> res = new HashSet<>();

        JSONArray arrJson = json.getJSONArray("languages");
        for(int i = 0; i < arrJson.length(); i++)
            res.add(Language.valueOf(arrJson.getString(i)));

        return res;
    }
}
