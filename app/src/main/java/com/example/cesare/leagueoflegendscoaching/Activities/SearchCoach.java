package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cesare.leagueoflegendscoaching.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class SearchCoach extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_coach);

        // Create elo spinner
        Spinner eloSpinner = (Spinner) findViewById(R.id.elo_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.elo_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eloSpinner.setAdapter(adapter);

        //create roles spinner
        Spinner role1Spinner = (Spinner) findViewById(R.id.role1_spinner);
        ArrayAdapter<CharSequence> r1Adapter = ArrayAdapter.createFromResource(this, R.array.role1_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role1Spinner.setAdapter(r1Adapter);

        // create a spinner for champions
        HashMap<Integer, String> spinnerMap = new HashMap<Integer, String>();


        InputStream championsList = getResources().openRawResource(R.raw.champions);
        String championsString = convertStreamToString(championsList);
        JSONArray json = null;
        try {
            json = new JSONArray(championsString);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json != null) {
            // prova
            for (int i = 0; i < json.length(); i++) {
                String championIndexName = null;
                try {
                    championIndexName = json.getString(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("CHAMP", championIndexName);
            }
        }

    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
