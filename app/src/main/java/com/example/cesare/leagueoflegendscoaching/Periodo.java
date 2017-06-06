package com.example.cesare.leagueoflegendscoaching;

/**
 * Created by cesare on 06/06/2017.
 */

public class Periodo {
    int inizio;
    int fine;
    boolean valido;

    Periodo(){}

    Periodo(int inizio, int fine){
        if (inizio >= fine){
            this.valido = false;
        }
        else {
            this.inizio = inizio;
            this.fine = fine;
            this.valido = true;
        }
    }

    public boolean disgiunti(Periodo p){
        return ((this.inizio < p.inizio && this.fine < p.inizio) || (this.inizio > p.fine && this.fine > p.fine));
    }

    public Periodo merge(Periodo p){
        Periodo t = new Periodo();
        t.inizio = Math.min(this.inizio, p.inizio);
        t.fine = Math.max(this.fine, p.fine);
        t.valido = true;
        return t;
    }
}
