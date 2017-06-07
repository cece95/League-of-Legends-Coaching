package com.example.cesare.leagueoflegendscoaching.Params;

import android.content.Context;

import com.example.cesare.leagueoflegendscoaching.Types.Elo;
import com.example.cesare.leagueoflegendscoaching.Types.Language;
import com.example.cesare.leagueoflegendscoaching.Types.Role;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;

/**
 * Created by cesare on 05/06/2017.
 */

public class CoachParams extends UserParams {
    Elo elo;
    HashSet<Language> languages;
    Role role1;
    Role role2;
    int cost;
    boolean upgrade;

    public CoachParams(String ign, String password, Context context, String requestType, Elo elo, HashSet<Language> languages, Role role1, Role role2, int cost, boolean upgrade) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        super(ign, password, context, requestType);
        this.elo = elo;
        this.languages = languages;
        this.role1 = role1;
        this.role2 = role2;
        this.cost = cost;
        this.upgrade = upgrade;
    }

    public Elo getElo() {
        return elo;
    }

    public HashSet<Language> getLanguages() {
        return languages;
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
