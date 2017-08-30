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
    String role1;
    String role2;
    int cost;

    public ReserveParams(boolean[] a, Context c, int start, int end, String coach, String date, String role1, String role2, int cost) {
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
        this.role1 = role1;
        this.role2 = role2;
        this.cost = cost;
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

    public String getRole1() {
        return role1;
    }

    public String getRole2() {
        return role2;
    }

    public int getCost() {
        return cost;
    }
}
