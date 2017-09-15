package com.example.cesare.leagueoflegendscoaching.Operations;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cesare.leagueoflegendscoaching.Operations.Params.SearchParams;
import com.example.cesare.leagueoflegendscoaching.Services.DAO;
import com.example.cesare.leagueoflegendscoaching.Services.Security;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SearchOperation  extends AsyncTask<SearchParams, Integer, JSONArray> {
    //Domain url
    final static String domain = "https://league-of-legends-coaching.herokuapp.com/";

    @Override
    protected JSONArray doInBackground(SearchParams... params) {
        JSONArray result = null;

        if (!Security.isNetworkAvailable(params[0].getContext())) {
            result.put(404);
            try {
                Log.d("result", (String) result.get(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        Log.d("NETWORK", "Network available");

        String route = "searchCoach";

        JSONObject jsonParam;
        try {
            jsonParam = params[0].prepareToSend();
            String res = DAO.doOperation(route, jsonParam);
            result = new JSONArray(res);
        } catch (JSONException | IOException e) {
            result.put(500);
        }

        return result;
    }


}
