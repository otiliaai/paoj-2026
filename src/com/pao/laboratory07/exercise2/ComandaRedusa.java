package com.pao.laboratory07.exercise2;

import java.util.Locale;

public final class ComandaRedusa extends Comanda{
    protected int discountProcent;

    ComandaRedusa(String nume, double pret, int discountProcent){
        super(nume, pret);
        this.discountProcent = discountProcent;
    }
    public ComandaRedusa(String nume, double pret, int discountProcent, String client){
        super(nume, pret,client);
        this.discountProcent = discountProcent;
    }

    public int getDiscountProcent(){
        return this.discountProcent;
    }
    @Override
    public double pretFinal() {
        return pret * (1 - discountProcent / 100.0);
    }

    @Override
    public String descriere() {
        return "DISCOUNTED: " + this.nume+ String.format(Locale.US,", pret: %.2f",this.pretFinal()) + " lei (-" + this.discountProcent+ "%) [" + this.stareComanda + "]";
    }
}

