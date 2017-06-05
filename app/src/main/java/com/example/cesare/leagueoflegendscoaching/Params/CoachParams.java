package com.example.cesare.leagueoflegendscoaching.Params;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by cesare on 05/06/2017.
 */

public class CoachParams extends UserParams {
    int elo;
    boolean[] languages;
    String role1;
    String role2;
    int cost;

    public CoachParams(String ign, String password, Context context, String requestType, int elo, boolean[] languages, String role1, String role2, int cost) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        super(ign, password, context, requestType);
        this.elo = elo;
        this.languages = languages;
        this.role1 = role1;
        this.role2 = role2;
        this.cost = cost;
    }

    public int getElo() {
        return elo;
    }

    public boolean[] getLanguages() {
        return languages;
    }

    public String getRole1() {
        return role1;
    }

    public String getRole2() {
        return role2;
    }

    public int getCost() {
        return cost;
    }
}
