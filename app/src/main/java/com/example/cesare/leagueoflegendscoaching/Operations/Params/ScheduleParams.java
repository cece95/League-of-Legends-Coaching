package com.example.cesare.leagueoflegendscoaching.Operations.Params;

import android.content.Context;

/**
 * Created by cesare on 24/08/2017.
 */

public class ScheduleParams {
    String user;
    Context context;
    String requestType;

    public ScheduleParams(String s, Context c, String r){
        this.user = s;
        this.context = c;
        this.requestType = r;
    }

    public String getUser() {
        return user;
    }

    public Context getContext() {
        return context;
    }

    public String getRequestType() { return requestType; }
}
