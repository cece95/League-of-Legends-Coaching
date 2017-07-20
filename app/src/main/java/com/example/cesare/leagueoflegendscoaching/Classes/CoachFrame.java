package com.example.cesare.leagueoflegendscoaching.Classes;

import android.media.Image;

import com.example.cesare.leagueoflegendscoaching.Types.Elo;
import com.example.cesare.leagueoflegendscoaching.Types.Language;
import com.example.cesare.leagueoflegendscoaching.Types.Role;

import java.util.HashSet;

/**
 * Created by asus on 12/07/2017.
 */

public class CoachFrame {
    String name;
    Elo elo;
    Role role1;
    Role role2;
    HashSet<Language> languages;
    Image eloImage;


    public CoachFrame(String name, Elo elo, Role role1, Role role2, HashSet<Language> languages) {
        this.name = name;
        this.elo = elo;
        this.role1 = role1;
        this.role2 = role2;
        this.languages = languages;
    }

}
