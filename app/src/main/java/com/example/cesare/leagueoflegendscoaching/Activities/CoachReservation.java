package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;

import com.example.cesare.leagueoflegendscoaching.Classes.Components.CoachFrame;
import com.example.cesare.leagueoflegendscoaching.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CoachReservation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_reservation);

        String jsonString = getIntent().getStringExtra("json");
        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);
            //coachFrame
            final CoachFrame coachFrame = new CoachFrame(json);
            ViewStub stub = (ViewStub) findViewById(R.id.stub);
            View frame = stub.inflate();
            coachFrame.createFrame(frame, false);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
