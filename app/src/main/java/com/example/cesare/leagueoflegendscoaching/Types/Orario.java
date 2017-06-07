package com.example.cesare.leagueoflegendscoaching.Types;

/**
 * Created by cesare on 06/06/2017.
 */

public class Orario {
    Day day;
    Periodo periodo;

    public Orario(Day day, int inizio, int fine){
        this.day = day;
        this.periodo = new Periodo(inizio, fine);
    }
}
