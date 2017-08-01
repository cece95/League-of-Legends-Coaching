package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import com.example.cesare.leagueoflegendscoaching.Classes.CoachFrame;
import com.example.cesare.leagueoflegendscoaching.Operations.SearchOperation;
import com.example.cesare.leagueoflegendscoaching.Params.SearchParams;
import com.example.cesare.leagueoflegendscoaching.R;
import com.example.cesare.leagueoflegendscoaching.Types.Language;
import com.example.cesare.leagueoflegendscoaching.Types.Role;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.concurrent.ExecutionException;

public class CoachesList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaches_list);
        ScrollView frameList = (ScrollView) findViewById(R.id.frameList);
        Intent intent = this.getIntent();
        SearchParams sParams = new SearchParams(
                intent.getStringExtra("nameCoach"),
                intent.getIntExtra("elo",0),
                Role.valueOf(intent.getStringExtra("role")),
                intent.getIntExtra("idChampion1", 0),
                intent.getIntExtra("idChampion2", 0),
                intent.getIntExtra("idChampion3", 0),
                intent.getIntExtra("cost", 0),
                (HashSet<Language>) intent.getSerializableExtra("languages"),
                CoachesList.this
        );

        try {
            JSONArray resultList = new SearchOperation().execute(sParams).get();
            if (resultList != null){
                for (int i = 0; i<resultList.length(); i++) {
                    CoachFrame coachFrame = new CoachFrame((JSONObject) resultList.get(i));
                    View frame = coachFrame.createFrame(CoachesList.this);
                    frameList.addView(frame);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
