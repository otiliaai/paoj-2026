package com.pao.proiect.biblioteca.model;

public enum TipEveniment {
    LANSARE("Lansare de carte"),
    ATELIER("Atelier creativ"),
    CONFERINTA("Conferinta literara"),
    EXPOZITIE("Expozitie tematica");

    private final String descriere;

    private TipEveniment(String var3) {
        this.descriere = var3;
    }

    public String getDescriere() {
        return this.descriere;
    }
}
