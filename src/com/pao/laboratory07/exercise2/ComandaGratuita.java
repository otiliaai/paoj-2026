package com.pao.laboratory07.exercise2;

import com.pao.laboratory07.exercise1.StareComanda;

public final class ComandaGratuita extends Comanda{

    public ComandaGratuita(String nume) {
        super(nume);
    }
    ComandaGratuita(String nume, double pret){
        super(nume, pret);
    }
    public ComandaGratuita(String nume, double pret, String client){
        super(nume, pret,client);
    }
    @Override
    public double pretFinal() {
        return this.pret;
    }

    @Override
    public String descriere() {
        return "GIFT: "+ this.nume + ", gratuit [" + this.stareComanda + "]";
    }
}
