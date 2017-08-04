package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.cesare.leagueoflegendscoaching.Classes.CoachFrame;
import com.example.cesare.leagueoflegendscoaching.R;

import org.json.JSONException;
import org.json.JSONObject;

public class CoachDetails extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_details);
        Intent intent = this.getIntent();
        try {
            JSONObject json = new JSONObject(intent.getStringExtra("info"));
            CoachFrame coachFrame = new CoachFrame(json);
            LayoutInflater layoutInflater = LayoutInflater.from(CoachDetails.this);
            View frame = coachFrame.createFrame(layoutInflater);
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.coachDetailsLayout);
            mainLayout.addView(frame);
            Button cya = (Button) findViewById(R.id.details);
            cya.setVisibility(View.GONE);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
