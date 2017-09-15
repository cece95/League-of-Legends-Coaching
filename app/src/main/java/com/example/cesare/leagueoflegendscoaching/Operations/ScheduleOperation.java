package com.example.cesare.leagueoflegendscoaching.Operations;

import android.os.AsyncTask;

import com.example.cesare.leagueoflegendscoaching.Operations.Params.ScheduleParams;
import com.example.cesare.leagueoflegendscoaching.Services.DAO;
import com.example.cesare.leagueoflegendscoaching.Services.Security;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ScheduleOperation extends AsyncTask<ScheduleParams, Integer, JSONObject> {
    //Domain url
    final static String domain = "https://league-of-legends-coaching.herokuapp.com/";

    @Override
    protected JSONObject doInBackground(ScheduleParams... params) {
        JSONObject result = null;

        if (!Security.isNetworkAvailable(params[0].getContext())) {
            try {
                result.put("code", 404);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        String requestType = params[0].getRequestType();
        String route = null;
        switch (requestType) {
            case "coachS":
                route = "getSchedule";
                break;
            case "user":
                route = "getUserReservation";
                break;
            case "coachR":
                route = "getCoachReservation";
                break;
        }

        JSONObject jsonParam;
        try {
            jsonParam = params[0].prepareToSend();
            String res = DAO.doOperation(route, jsonParam);
            result = new JSONObject(res);

        } catch (JSONException | IOException e) {
            try {
                result.put("code", 500);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }

        return result;
    }
}