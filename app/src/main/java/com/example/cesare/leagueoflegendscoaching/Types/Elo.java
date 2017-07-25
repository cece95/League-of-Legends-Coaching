package com.example.cesare.leagueoflegendscoaching.Types;

/**
 * Created by cesare on 07/06/2017.
 */

public enum Elo {
    Bronze, Silver, Gold, Platinum, Diamond, Master, Challenger;

    public int EloToInt() {
        return this.ordinal();
    }
}
