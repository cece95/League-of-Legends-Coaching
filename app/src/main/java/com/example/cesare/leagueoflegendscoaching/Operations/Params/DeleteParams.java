package com.example.cesare.leagueoflegendscoaching.Operations.Params;

import android.content.Context;

import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.LoggedUser;

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

    public DeleteParams(Context c, int start, int end, String coach, String date){
        this.context = c;
        this.start = start;
        this.end = end;
        this.user = LoggedUser.getIstance(null, null, false).getIgn();
        this.coach = coach;
        this.date = date;
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
}
