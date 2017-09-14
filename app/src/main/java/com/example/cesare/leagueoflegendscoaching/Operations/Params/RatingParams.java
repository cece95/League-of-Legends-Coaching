package com.example.cesare.leagueoflegendscoaching.Operations.Params;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cesare on 14/09/2017.
 */

public class RatingParams {
    String student;
    String coach;
    int rate;
    String requestType;
    Context context;

    public RatingParams(String student, String coach, int rate, String type, Context context){
        this.student = student;
        this.coach = coach;
        this.rate = rate;
        this.requestType = type;
        this.context = context;
    }

    public JSONObject prepareToSend() throws JSONException{
        JSONObject jsonParam = new JSONObject();

        jsonParam.put("student", student);
        jsonParam.put("coach", coach);
        jsonParam.put("rate", rate);

        return  jsonParam;
    }

    public String getStudent() {
        return student;
    }

    public String getCoach() {
        return coach;
    }

    public int getRate() {
        return rate;
    }

    public String getRequestType() {
        return requestType;
    }

    public Context getContext() {
        return context;
    }
}
