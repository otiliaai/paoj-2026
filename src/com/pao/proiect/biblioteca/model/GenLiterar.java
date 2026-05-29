package com.pao.proiect.biblioteca.model;

public enum GenLiterar {
    ROMAN("Roman"),
    POEZIE("Poezie"),
    DRAMA("Drama"),
    STIINTA_FICTIUNE("Stiinta Fictiune"),
    FANTEZIE("Fantezie"),
    BIOGRAFIE("Biografie"),
    ISTORIE("Istorie");

    private final String eticheta;

    private GenLiterar(String var3) {
        this.eticheta = var3;
    }

    public String getEticheta() {
        return this.eticheta;
    }
}
