package com.example.cesare.leagueoflegendscoaching.Operations.Params;

import android.content.Context;

import com.example.cesare.leagueoflegendscoaching.Types.Language;
import com.example.cesare.leagueoflegendscoaching.Types.Role;

import java.util.HashSet;

/**
 * Created by asus on 20/07/2017.
 */

public class SearchParams {
    String nameCoach;
    int elo;
    Role role;
    int idChampion1;
    int idChampion2;
    int idChampion3;
    int cost;
    HashSet<Language> languages;
    Context context;

    public SearchParams(String nameCoach, int elo, Role role, int idChampion1, int idChampion2, int idChampion3, int cost, HashSet<Language> languages, Context context) {
        this.nameCoach = nameCoach;
        this.elo = elo;
        this.role = role;
        this.idChampion1 = idChampion1;
        this.idChampion2 = idChampion2;

        this.idChampion3 = idChampion3;
        this.cost = cost;
        this.languages = languages;
        this.context = context;
    }

    public String getNameCoach() {
        return nameCoach;
    }

    public void setNameCoach(String nameCoach) {
        this.nameCoach = nameCoach;
    }

    public int getElo() {
        return elo;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    public int getIdChampion1() {
        return idChampion1;
    }

    public void setIdChampion1(int idChampion1) {
        this.idChampion1 = idChampion1;
    }

    public int getIdChampion2() {
        return idChampion2;
    }

    public void setIdChampion2(int idChampion2) {
        this.idChampion2 = idChampion2;
    }

    public int getIdChampion3() {
        return idChampion3;
    }

    public void setIdChampion3(int idChampion3) {
        this.idChampion3 = idChampion3;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public HashSet<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(HashSet<Language> languages) {
        this.languages = languages;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Role getRole() {return role;}

    public void setRole(Role role) {this.role = role;}
}
