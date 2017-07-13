package com.example.cesare.leagueoflegendscoaching.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cesare.leagueoflegendscoaching.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

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
        Spinner role1Spinner = (Spinner) findViewById(R.id.role_spinner);
        ArrayAdapter<CharSequence> r1Adapter = ArrayAdapter.createFromResource(this, R.array.role1_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role1Spinner.setAdapter(r1Adapter);

        // create a spinner for champions
        InputStream championsList = getResources().openRawResource(R.raw.champions);
        String championsString = convertStreamToString(championsList);

        JSONObject json = null;
        HashMap<String, Integer> spinnerMap = null;

        try {
            json = new JSONObject(championsString);
            spinnerMap = getChampionsMap(json);
            ArrayList<String> spinnerList = new ArrayList<String>(spinnerMap.keySet());
            Collections.sort(spinnerList);

            Spinner champion1Spinner = (Spinner) findViewById(R.id.champions1_spinner);
            ArrayAdapter<String> c1Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            champion1Spinner.setAdapter(c1Adapter);

            Spinner champion2Spinner = (Spinner) findViewById(R.id.champions2_spinner);
            ArrayAdapter<String> c2Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            champion2Spinner.setAdapter(c2Adapter);

            Spinner champion3Spinner = (Spinner) findViewById(R.id.champions3_spinner);
            ArrayAdapter<String> c3Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            champion3Spinner.setAdapter(c3Adapter);

        } catch (JSONException e) {
            e.printStackTrace();
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

    private HashMap<String, Integer> getChampionsMap(JSONObject json) throws JSONException {
        HashMap<String, Integer> res = new HashMap<>();
        Iterator<String> keys = json.keys();

        while (keys.hasNext()) {
            Integer value = Integer.parseInt(keys.next());
            String key = null;
            key = json.getString(Integer.toString(value));

            res.put(key, value);
        }

        return res;
    }
}


