package com.pao.laboratory07.exercise2;

import com.pao.laboratory07.exercise1.StareComanda;

public final class ComandaGratuita extends Comanda{

    ComandaGratuita(String nume ) {
        super(nume);
    }
    ComandaGratuita(String nume, double pret){
        super(nume, pret);
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
