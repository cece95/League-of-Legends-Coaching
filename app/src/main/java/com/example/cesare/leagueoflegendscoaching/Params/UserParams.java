package com.example.cesare.leagueoflegendscoaching.Params;

import android.content.Context;

import com.example.cesare.leagueoflegendscoaching.Security;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by cesare on 24/05/2017.
 */

public class UserParams {
    String ign;
    String password;
    String requestType;
    Context context;

    public UserParams(String ign, String password, Context context, String requestType) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.ign = ign;
        this.password = Security.SHA1(password);
        this.context = context;
        this.requestType = requestType;
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
}
