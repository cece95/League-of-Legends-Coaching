package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.cesare.leagueoflegendscoaching.Classes.ChampionFrame;
import com.example.cesare.leagueoflegendscoaching.Classes.ChampionFrameAdapter;
import com.example.cesare.leagueoflegendscoaching.Classes.CoachFrame;
import com.example.cesare.leagueoflegendscoaching.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CoachDetails extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_details);
        Intent intent = this.getIntent();
        try {
            JSONObject json = new JSONObject(intent.getStringExtra("info"));
            JSONArray champions = json.getJSONArray("champions");
            List<ChampionFrame> championFrameList = new ArrayList<>();
            if (champions != null) {
                for (int i = 0; i < champions.length(); i++) {
                    ChampionFrame championFrame = new ChampionFrame((JSONObject) champions.get(i));
                    championFrameList.add(championFrame);
                }
                setListAdapter(new ChampionFrameAdapter(this, R.layout.coach_frame, championFrameList));

                //coachFrame
                final CoachFrame coachFrame = new CoachFrame(json);
                LayoutInflater layoutInflater = LayoutInflater.from(CoachDetails.this);
                View frame = coachFrame.createFrame(layoutInflater);
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.coachDetailsLayout);
                mainLayout.addView(frame);
                Button cya = (Button) findViewById(R.id.details);
                cya.setVisibility(View.GONE);

                //opgg button
                Button opggButton = (Button) findViewById(R.id.opggButton);
                opggButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "https://euw.op.gg/summoner/userName="+coachFrame.getName();
                        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });

                //bookButton
                
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
