package com.example.cesare.leagueoflegendscoaching.Operations.Params;

import android.content.Context;

import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;

import org.json.JSONArray;

/**
 * Created by asus on 28/08/2017.
 */

public class ReserveParams {
    JSONArray array;
    Context context;
    int start;
    int end;
    String user;
    String coach;
    String date;

    public ReserveParams(boolean[] a, Context c, int start, int end, String coach, String date) {
        this.array = new JSONArray();
        for (int i = 0; i<a.length; i++){
            array.put(a[i]);
        }
        this.context = c;
        this.start = start;
        this.end = end+1;
        this.user = LoggedUser.getIstance(null, null, false).getIgn();
        this.coach = coach;
        this.date = date;
    }

    public JSONArray getArray() {
        return array;
    }

    public Context getContext(){
        return context;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public String getUser() {
        return user;
    }

    public String getCoach() {
        return coach;
    }

    public String getDate() {
        return date;
    }
}
