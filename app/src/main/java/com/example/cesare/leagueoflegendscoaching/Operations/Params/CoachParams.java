package com.example.cesare.leagueoflegendscoaching.Operations.Params;

import android.content.Context;

import com.example.cesare.leagueoflegendscoaching.Types.Language;
import com.example.cesare.leagueoflegendscoaching.Types.Role;

import org.json.JSONArray;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

/**
 * Created by cesare on 05/06/2017.
 */

public class CoachParams extends UserParams implements Serializable{
    int elo;
    public HashSet<Language> languages;
    Role role1;
    Role role2;
    int cost;
    boolean upgrade;

    public CoachParams(String ign, String password, Context context, String requestType, int elo, HashSet<Language> languages, Role role1, Role role2, int cost, boolean upgrade) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        super(ign, password, context, requestType);
        this.elo = elo;
        this.languages = languages;
        this.role1 = role1;
        this.role2 = role2;
        this.cost = cost;
        this.upgrade = upgrade;
    }

    public int getElo() {
        return elo;
    }

    public JSONArray getLanguages() {
        JSONArray res = new JSONArray();
        for (Language language : this.languages) {
            res.put(language);
        }
        return res;
    }

    public Role getRole1() {
        return role1;
    }

    public Role getRole2() {
        return role2;
    }

    public int getCost() {
        return cost;
    }

    public boolean getUpgrade() { return upgrade; }
}
