package com.pao.laboratory07.exercise2;
import com.pao.laboratory07.exercise1.StareComanda;

public abstract sealed class Comanda permits ComandaStandard, ComandaRedusa, ComandaGratuita {
    protected String nume;
    protected double pret;
    protected StareComanda stareComanda;

    Comanda(String nume) {
        this.nume = nume;
        this.stareComanda = StareComanda.PLACED;
    }
    Comanda(String nume, double pret ){
        this.nume = nume;
        this.pret = pret;
        this.stareComanda = StareComanda.PLACED;
    }

    public abstract double pretFinal();
    public abstract String descriere();
}
