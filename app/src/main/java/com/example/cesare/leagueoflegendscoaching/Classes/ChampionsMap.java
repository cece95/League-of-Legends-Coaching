package com.example.cesare.leagueoflegendscoaching.Classes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by asus on 25/07/2017.
 */

public class ChampionsMap {

    public static HashMap<String, Integer> spinnerMap;
    public static HashMap<Integer, String> idToChamp;

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
