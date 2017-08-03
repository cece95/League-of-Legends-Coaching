package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.cesare.leagueoflegendscoaching.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CoachDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_details);
        Intent intent = this.getIntent();
        try {
            JSONObject json = new JSONObject(intent.getStringExtra("info"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
