package com.example.cesare.leagueoflegendscoaching;

import android.content.Context;

/**
 * Created by cesare on 24/05/2017.
 */

public class RegistrationParams {
    String ign;
    String password;
    Context context;

    RegistrationParams(String ign, String password, Context context){
        this.ign = ign;
        this.password = password;
        this.context = context;
    }
}
