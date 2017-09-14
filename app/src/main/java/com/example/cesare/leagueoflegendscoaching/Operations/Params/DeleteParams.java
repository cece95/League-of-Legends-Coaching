package com.example.cesare.leagueoflegendscoaching.Operations.Params;

import android.content.Context;

import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cesare on 30/08/2017.
 */

public class DeleteParams {
    Context context;
    int start;
    int end;
    String user;
    String coach;
    String date;
    String key;

    public DeleteParams(Context c, int start, int end, String coach, String date, String key){
        this.context = c;
        this.start = start;
        this.end = end;
        this.user = LoggedUser.getIstance(null, null, false, context).getIgn();
        this.coach = coach;
        this.date = date;
        this.key = key;
    }

    public JSONObject prepareToSend() throws JSONException {
        JSONObject jsonParam = new JSONObject();

        jsonParam.put("start", start);
        jsonParam.put("end", end);
        jsonParam.put("student", user);
        jsonParam.put("coach", coach);
        jsonParam.put("date", date);
        jsonParam.put("key", key);

        return jsonParam;
    }

    public Context getContext() {
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

    public String getKey() {return key; }
}
