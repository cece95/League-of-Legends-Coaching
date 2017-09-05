package com.example.cesare.leagueoflegendscoaching.Operations;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cesare.leagueoflegendscoaching.Classes.Security;
import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.Schedule;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.CoachParams;

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
 * Created by cesare on 05/06/2017.
 */

public class CoachOperation extends AsyncTask<CoachParams, Integer, Integer> {
    //Domain url
    final static String domain = "https://league-of-legends-coaching.herokuapp.com/";

    @Override
    protected Integer doInBackground(CoachParams... params) {
        int control = 0;

        if(!Security.isNetworkAvailable(params[0].getContext())){
            control = 404;
            return control;
        }

        Log.d("NETWORK", "Network available");

        //request url creation
        String route = null;
        String requestType = params[0].getRequestType();
        if (requestType == "register") {
            route = "coachRegistration/";
        }
        else if(requestType == "updateInfo"){
            route = "updateInfo/";
        }

        String complete_url = domain + route;

        Log.d("URL", "complete_url: "+complete_url);

        URL url = null;

        try {
            url = new URL(complete_url);
        } catch (MalformedURLException e) {
            control = 3;
            e.printStackTrace();
        }

        Log.d("SHA1", "Sha1 password: "+params[0].getPassword());

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
                jsonParam.put("ign", params[0].getIgn());
                jsonParam.put("password", params[0].getPassword());
                jsonParam.put("elo", params[0].getElo());
                jsonParam.put("languages", params[0].getLanguages());
                jsonParam.put("role1", params[0].getRole1());
                jsonParam.put("role2", params[0].getRole2());
                jsonParam.put("cost", params[0].getCost());
                jsonParam.put("upgrade", params[0].getUpgrade());
                jsonParam.put("schedule", Schedule.getIstance().getSchedule());

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
                    JSONObject jObject = new JSONObject(sb.toString());

                    int code = jObject.getInt("code");
                    control = code;

                }
            } catch (UnsupportedEncodingException e) {
                control = 4;
                e.printStackTrace();
            } catch (IOException e) {
                control = 5;
                e.printStackTrace();
            } catch (JSONException e) {
                control = 6;
                e.printStackTrace();
            }
        }

        return control;
    }
}
