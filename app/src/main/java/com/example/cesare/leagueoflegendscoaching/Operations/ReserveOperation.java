package com.example.cesare.leagueoflegendscoaching.Operations;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cesare.leagueoflegendscoaching.Operations.Params.ReserveParams;
import com.example.cesare.leagueoflegendscoaching.Services.DAO;
import com.example.cesare.leagueoflegendscoaching.Services.Security;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ReserveOperation extends AsyncTask<ReserveParams, Integer, Integer> {
    //Domain url
    final static String domain = "https://league-of-legends-coaching.herokuapp.com/";

    @Override
    protected Integer doInBackground(ReserveParams... params) {
        int control;

        if(!Security.isNetworkAvailable(params[0].getContext())){
            control = 404;
            return control;
        }

        Log.d("NETWORK", "Network available");

        //request url creation
        String route = "saveReservation/";

        JSONObject jsonParam;
        try {
            jsonParam = params[0].prepareToSend();
            String res = DAO.doOperation(route, jsonParam);
            JSONObject jObject = new JSONObject(res);
            control = jObject.getInt("code");
        } catch (JSONException | IOException e) {
            control = 500;
        }

        return control;
    }
}
