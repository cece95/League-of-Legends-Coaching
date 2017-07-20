package com.example.cesare.leagueoflegendscoaching.Params;

import com.example.cesare.leagueoflegendscoaching.Types.Elo;
import com.example.cesare.leagueoflegendscoaching.Types.Language;

import java.util.HashSet;

/**
 * Created by asus on 20/07/2017.
 */

public class SearchParams {
    String nameCoach;
    Elo elo;
    int idChampion1;
    int idChampion2;
    int idChampion3;
    int cost;
    HashSet<Language> languages;

    public SearchParams(String nameCoach, Elo elo, int idChampion1, int idChampion2, int idChampion3, int cost, HashSet<Language> languages) {
        this.nameCoach = nameCoach;
        this.elo = elo;
        this.idChampion1 = idChampion1;
        this.idChampion2 = idChampion2;
        this.idChampion3 = idChampion3;
        this.cost = cost;
        this.languages = languages;
    }
}
