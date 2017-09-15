package com.example.cesare.leagueoflegendscoaching.Operations;

import android.os.AsyncTask;

import com.example.cesare.leagueoflegendscoaching.Operations.Params.DeleteParams;
import com.example.cesare.leagueoflegendscoaching.Services.DAO;
import com.example.cesare.leagueoflegendscoaching.Services.Security;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DeleteOperation extends AsyncTask<DeleteParams, Integer, Integer> {

    @Override
    protected Integer doInBackground(DeleteParams... params) {
        int control;

        if(!Security.isNetworkAvailable(params[0].getContext())){
            control = 404;
            return control;
        }

        //request url creation
        String route = "deleteReservation/";

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
