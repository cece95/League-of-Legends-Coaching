package com.example.cesare.leagueoflegendscoaching.Services;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

public class DAO {
    final private static String domain = "https://league-of-legends-coaching.herokuapp.com/";

    public static HashMap<String, Integer> spinnerMap;
    public static HashMap<Integer, String> idToChamp;

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

    static public JSONObject readJson(InputStream is) {
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
        JSONObject json = null;
        try {
            json = new JSONObject(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    static public HashMap<String, Integer> getChampionsMap(JSONObject json) throws JSONException {
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

    static public HashMap<Integer,String> getIdToChampMap(HashMap<String, Integer> map){
        HashMap<Integer, String> reversedHashMap = new HashMap<>();
        for (String key : map.keySet()){
            reversedHashMap.put(map.get(key), key);
        }
        return reversedHashMap;
    }


}