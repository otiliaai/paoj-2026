package com.pao.proiect.biblioteca.model;

public enum RolAngajat {
    BIBLIOTECAR("Bibliotecar", "Gestioneaza colectia de carti si ajuta cititorii"),
    MANAGER("Manager", "Coordoneaza echipa si activitatile bibliotecii"),
    ARHIVAR("Arhivar", "Administreaza arhiva si documentele istorice"),
    RECEPTIONER("Receptioner", "Primeste vizitatorii si gestioneaza programarile");

    private final String titlu;
    private final String descriere;

    private RolAngajat(String var3, String var4) {
        this.titlu = var3;
        this.descriere = var4;
    }

    public String getTitlu() {
        return this.titlu;
    }

    public String getDescriere() {
        return this.descriere;
    }
}
