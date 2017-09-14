package com.example.cesare.leagueoflegendscoaching.Operations.Params;

import android.content.Context;

import com.example.cesare.leagueoflegendscoaching.Classes.Singletons.Schedule;
import com.example.cesare.leagueoflegendscoaching.Types.Language;
import com.example.cesare.leagueoflegendscoaching.Types.Role;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public CoachParams(String ign, String password, Context context, String requestType, String token,int elo, HashSet<Language> languages, Role role1, Role role2, int cost, boolean upgrade) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        super(ign, password, context, requestType, token);
        this.elo = elo;
        this.languages = languages;
        this.role1 = role1;
        this.role2 = role2;
        this.cost = cost;
        this.upgrade = upgrade;
    }

    public JSONObject prepareToSend() throws JSONException {
        JSONObject jsonParam = new JSONObject();

        jsonParam.put("ign", ign);
        jsonParam.put("password", password);
        jsonParam.put("elo", elo);
        jsonParam.put("languages", getLanguages());
        jsonParam.put("role1", role1);
        jsonParam.put("role2", role2);
        jsonParam.put("cost", cost);
        jsonParam.put("upgrade", upgrade);
        jsonParam.put("schedule", Schedule.getIstance().getSchedule());

        return jsonParam;
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
