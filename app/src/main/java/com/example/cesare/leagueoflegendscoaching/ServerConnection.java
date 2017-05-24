package com.example.cesare.leagueoflegendscoaching;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;


/**
 * Created by cesare on 24/05/2017.
 */

public class ServerConnection {
    //Domain url
    final static String domain = "https://league-of-legends-coaching.herokuapp.com/";

    public static int userRegistration(String name, String ign, String password) {
        int control = 0;
        String sha1_password = null;

        //request url creation
        String route = "userRegistration/";
        String complete_url = domain + route;
        URL url = null;

        try {
            url = new URL(complete_url);
        } catch (MalformedURLException e) {
            control = 3;
            e.printStackTrace();
        }

        try {
            sha1_password = Security.SHA1(password);
        } catch (NoSuchAlgorithmException e) {
            control = 1;
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            control = 2;
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
                jsonParam.put("name", name);
                jsonParam.put("ign", ign);
                jsonParam.put("password", sha1_password);
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(jsonParam.toString());
                out.close();

                int HttpResult = urlConnection.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    StringBuilder sb = new StringBuilder();

                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream(), "utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    if (sb.toString().equals("ok")) {
                        control = 200;
                    }
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
