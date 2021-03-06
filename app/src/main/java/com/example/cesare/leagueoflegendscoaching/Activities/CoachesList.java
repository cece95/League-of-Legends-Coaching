package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.example.cesare.leagueoflegendscoaching.Classes.Components.Adapters.CoachFrameAdapter;
import com.example.cesare.leagueoflegendscoaching.Classes.Components.CoachFrame;
import com.example.cesare.leagueoflegendscoaching.Classes.Listeners.ShakeDetector;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.SearchParams;
import com.example.cesare.leagueoflegendscoaching.Operations.SearchOperation;
import com.example.cesare.leagueoflegendscoaching.R;
import com.example.cesare.leagueoflegendscoaching.Types.Language;
import com.example.cesare.leagueoflegendscoaching.Types.Role;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CoachesList extends ListActivity {

    private ShakeDetector shaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaches_list);
        shaker = new ShakeDetector(this);

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
            List<CoachFrame> coachFrameList = new ArrayList<>();
            if (resultList != null){
                for (int i = 0; i<resultList.length(); i++) {
                    CoachFrame coachFrame = new CoachFrame((JSONObject) resultList.get(i));
                    coachFrameList.add(coachFrame);
                }
                setListAdapter(new CoachFrameAdapter(this, R.layout.coach_frame, coachFrameList));
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
