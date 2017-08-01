package com.example.cesare.leagueoflegendscoaching.Operations;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cesare.leagueoflegendscoaching.Params.SearchParams;
import com.example.cesare.leagueoflegendscoaching.Security;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by asus on 20/07/2017.
 */

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
        String complete_url = domain + route;

        URL url = null;
        try {
            url = new URL(complete_url);
        } catch (MalformedURLException e) {
            result.put(3);
            e.printStackTrace();
        }

        if (url != null) {
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setReadTimeout(10000);
                urlConnection.setRequestProperty("Content-Type", "application/json");

                urlConnection.connect();

                JSONObject jsonParam = new JSONObject();

                jsonParam.put("nameCoach", params[0].getNameCoach());
                jsonParam.put("elo", params[0].getElo());
                jsonParam.put("role", params[0].getRole());
                jsonParam.put("idChampion1", params[0].getIdChampion1());
                jsonParam.put("idChampion2", params[0].getIdChampion2());
                jsonParam.put("idChampion3", params[0].getIdChampion3());
                jsonParam.put("cost", params[0].getCost());
                jsonParam.put("languages", params[0].getLanguages());


                Log.d("JSON", "Json: "+jsonParam);

                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(jsonParam.toString());
                out.close();

                int HttpResult = urlConnection.getResponseCode();
                Log.d("RESPONSE CODE", Integer.toString(HttpResult));

                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    StringBuilder sb = new StringBuilder();

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(), "utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    Log.d("RESPONSE", sb.toString());
                    JSONArray jresult = new JSONArray(sb.toString());

                    result = jresult;
                }
            } catch (UnsupportedEncodingException e) {
                result.put(4);
                e.printStackTrace();
            } catch (IOException e) {
                result.put(5);
                e.printStackTrace();
            } catch (JSONException e) {
                result.put(6);
                e.printStackTrace();
            }
        }
        return result;
    }


}
