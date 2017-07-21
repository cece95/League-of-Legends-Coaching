package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.cesare.leagueoflegendscoaching.Operations.SearchOperation;
import com.example.cesare.leagueoflegendscoaching.Operations.UserOperation;
import com.example.cesare.leagueoflegendscoaching.Params.SearchParams;
import com.example.cesare.leagueoflegendscoaching.R;

import org.json.JSONArray;

import java.util.concurrent.ExecutionException;

public class CoachesList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coaches_list);
        Intent intent = this.getIntent();
        SearchParams searchParams = intent.getParcelableExtra("sParams");
        searchParams.setContext(CoachesList.this);
        try {
            JSONArray coaches = new SearchOperation().execute(searchParams).get();
        } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
        }
    }


}
