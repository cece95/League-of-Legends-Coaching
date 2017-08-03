package com.example.cesare.leagueoflegendscoaching.Classes;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.cesare.leagueoflegendscoaching.Activities.CoachDetails;

import org.json.JSONObject;

/**
 * Created by cesare on 03/08/2017.
 */

public class DetailsListener implements View.OnClickListener {

    JSONObject info;

    public DetailsListener(JSONObject json){
        this.info = json;
    }

    @Override
    public void onClick(View v) {
        Log.d("Context", v.getContext().toString());
        Intent intent = new Intent(v.getContext(), CoachDetails.class);
        intent.putExtra("info", this.info.toString());
        v.getContext().startActivity(intent);
    }
}
