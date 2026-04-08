package com.pao.laboratory07.exercise2;

import java.util.Locale;

public final class ComandaStandard extends Comanda {

    public ComandaStandard(String nume, double pret) {
        super(nume,pret);
    }
    public ComandaStandard(String nume, double pret, String client) {
        super(nume,pret,client);

    }
    @Override
    public double pretFinal() {
        return this.pret;
    }

    @Override
    public String descriere() {
        return "STANDARD: " + this.nume + String.format(Locale.US,", pret: %.2f",this.pret)+ " lei [" + this.stareComanda + "]" ;
    }
}
