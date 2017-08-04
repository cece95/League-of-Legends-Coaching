package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

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
            Log.d("INFO", json.toString());

            //coachFrame
            final CoachFrame coachFrame = new CoachFrame(json);
            ViewStub stub = (ViewStub) findViewById(R.id.stub);
            View frame = stub.inflate();
            coachFrame.createFrame(frame, false);

            //championList
            JSONArray champions = json.getJSONArray("champions");
            List<ChampionFrame> championFrameList = new ArrayList<>();
            if (champions != null) {
                for (int i = 0; i < champions.length(); i++) {
                    ChampionFrame championFrame = new ChampionFrame((JSONObject) champions.get(i));
                    championFrameList.add(championFrame);
                }
                setListAdapter(new ChampionFrameAdapter(this, R.layout.champion_frame, championFrameList));

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
