package com.example.cesare.leagueoflegendscoaching.Classes.Components;

import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cesare on 29/08/2017.
 */

public class ReservationFrame implements Serializable{
    Date date;
    String coach;
    int end;
    int start;

    public ReservationFrame(String date, JSONObject json) throws JSONException {
        this.date = new Date(date);
        this.coach = json.getString("coach");
        this.start = json.getInt("start");
        this.end = json.getInt("end");
    }

    public View createFrame(View frame){
        return frame;
    }

    public Date getDate() {
        return date;
    }

    public String getCoach() {
        return coach;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }
}
