package com.example.cesare.leagueoflegendscoaching.Classes.Singletons;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by cesare on 04/08/2017.
 */

public class Schedule {
    private static Schedule mInstance = null;
    private boolean[][] schedule;

    private final int days = 7;
    private final int hours = 24;

    private Schedule(){
        schedule = new boolean[days][hours];
        for (int i=0; i<days; i++){
            for (int j=0; j<hours; j++){
                schedule[i][j] = false;
            }
        }
    }

    public static Schedule getIstance(){
        if(mInstance == null)
        {
            mInstance = new Schedule();
        }
        return mInstance;
    }

    public boolean[] getDay(int d){
        return schedule[d];
    }

    public void setDay(int d, boolean[] day){
        schedule[d] = day;
    }

    public JSONArray getSchedule() throws JSONException {
        JSONArray res = new JSONArray();
        for (int i=0; i<days; i++){
            JSONArray day = new JSONArray();
            for (int j=0; j<hours; j++){
                day.put(schedule[i][j]);
            }
            res.put(day);
        }
        return res;
    }
}
