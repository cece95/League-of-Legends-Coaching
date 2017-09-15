package com.example.cesare.leagueoflegendscoaching.Operations.Params;

import android.content.Context;

import com.example.cesare.leagueoflegendscoaching.Services.Security;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class UserParams {
    String ign;
    String password;
    private String requestType;
    private String token;
    private Context context;

    public UserParams(String ign, String password, Context context, String requestType, String token) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.ign = ign;
        this.password = Security.SHA1(password);
        this.context = context;
        this.requestType = requestType;
        this.token = token;
    }

    public JSONObject prepareToSend() throws JSONException {
        JSONObject jsonParam = new JSONObject();

        jsonParam.put("ign", ign);
        jsonParam.put("password", password);
        jsonParam.put("token", token);

        return  jsonParam;
    }

    public String getIgn() {
        return ign;
    }

    public String getPassword() {
        return password;
    }

    public String getRequestType() {
        return requestType;
    }

    public Context getContext() {
        return context;
    }

    public String getToken() {return token; }


}
