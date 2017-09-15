package com.example.cesare.leagueoflegendscoaching.Services;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DAO {
    final private static String domain = "https://league-of-legends-coaching.herokuapp.com/";

    public static String doOperation(String route, JSONObject params) throws IOException {
        String complete_url = domain + route;
        URL url = new URL(complete_url);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setRequestMethod("POST");
        urlConnection.setUseCaches(false);
        urlConnection.setConnectTimeout(10000);
        urlConnection.setReadTimeout(10000);
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.connect();

        OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
        out.write(params.toString());
        out.close();

        int HttpResult = urlConnection.getResponseCode();
        if (HttpResult == HttpURLConnection.HTTP_OK) {
            StringBuilder sb = new StringBuilder();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream(), "utf-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            br.close();

            return sb.toString();
        }
        return "";
    }
}