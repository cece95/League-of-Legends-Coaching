package com.example.cesare.leagueoflegendscoaching.Operations;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cesare.leagueoflegendscoaching.Classes.Security;
import com.example.cesare.leagueoflegendscoaching.Operations.Params.DeleteParams;

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
 * Created by cesare on 30/08/2017.
 */

public class DeleteOperation extends AsyncTask<DeleteParams, Integer, Integer> {
    //Domain url
    final static String domain = "https://league-of-legends-coaching.herokuapp.com/";

    @Override
    protected Integer doInBackground(DeleteParams... params) {
        int control = 0;

        if(!Security.isNetworkAvailable(params[0].getContext())){
            control = 404;
            return control;
        }

        Log.d("NETWORK", "Network available");

        //request url creation
        String route = null;
        route = "deleteReservation/";

        String complete_url = domain + route;

        Log.d("URL", "complete_url: "+complete_url);

        URL url = null;

        try {
            url = new URL(complete_url);
        } catch (MalformedURLException e) {
            control = 3;
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

                JSONObject jsonParam = params[0].prepareToSend();

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
