package com.example.cesare.leagueoflegendscoaching.Operations.Params;

import android.content.Context;

/**
 * Created by cesare on 24/08/2017.
 */

public class ScheduleParams {
    String coach;
    Context context;

    public ScheduleParams(String s, Context c){
        this.coach = s;
        this.context = c;
    }

    public String getCoach() {
        return coach;
    }

    public Context getContext() {
        return context;
    }
}
