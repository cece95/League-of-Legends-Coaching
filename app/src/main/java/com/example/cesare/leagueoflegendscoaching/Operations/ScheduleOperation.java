package com.example.cesare.leagueoflegendscoaching.Operations;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cesare.leagueoflegendscoaching.Classes.Security;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.ScheduleParams;

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
 * Created by cesare on 24/08/2017.
 */

public class ScheduleOperation extends AsyncTask<ScheduleParams, Integer, JSONObject> {
    //Domain url
    final static String domain = "https://league-of-legends-coaching.herokuapp.com/";

    @Override
    protected JSONObject doInBackground(ScheduleParams... params) {
        JSONObject result = null;

        if (!Security.isNetworkAvailable(params[0].getContext())) {
            try {
                result.put("code", 404);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        String requestType = params[0].getRequestType();
        String route = null;
        if (requestType == "coachS") {
            route = "getSchedule";
        }
        else if (requestType == "user"){
            route = "getUserReservation";
        }
        else if (requestType == "coachR"){
            route = "getCoachReservation";
        }
        String complete_url = domain + route;

        URL url = null;
        try {
            url = new URL(complete_url);
        } catch (MalformedURLException e) {
            try {
                result.put("code", 3);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
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

                jsonParam.put("user", params[0].getUser());

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
                    JSONObject jresult = new JSONObject(sb.toString());

                    result = jresult;
                }
            } catch (UnsupportedEncodingException e) {
                try {
                    result.put("code", 4);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            } catch (IOException e) {
                try {
                    result.put("code", 5);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            } catch (JSONException e) {
                try {
                    result.put("code", 6);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }
        }
        return result;
    }
}
