package com.pao.proiect.biblioteca.model;

public enum StatusImprumut {
    ACTIV("Activ"),
    RETURNAT("Returnat"),
    INTARZIAT("Intarziat"),
    ANULAT("Anulat");

    private final String descriere;

    private StatusImprumut(String var3) {
        this.descriere = var3;
    }

    public String getDescriere() {
        return this.descriere;
    }
}
