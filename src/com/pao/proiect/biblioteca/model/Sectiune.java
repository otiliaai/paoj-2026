package com.pao.proiect.biblioteca.model;

import java.util.TreeSet;

public class Sectiune {
    private final int id;
    private String nume;
    private String descriere;
    private GenLiterar gen;
    private TreeSet<Carte> carti;
    private static int contor = 1;

    public Sectiune(String var1, String var2, GenLiterar var3) {
        this.id = contor++;
        this.nume = var1;
        this.descriere = var2;
        this.gen = var3;
        this.carti = new TreeSet();
    }

    public void adaugaCarte(Carte var1) {
        this.carti.add(var1);
    }

    public int getId() {
        return this.id;
    }

    public String getNume() {
        return this.nume;
    }

    public String getDescriere() {
        return this.descriere;
    }

    public GenLiterar getGen() {
        return this.gen;
    }

    public TreeSet<Carte> getCarti() {
        return this.carti;
    }

    public void setNume(String var1) {
        this.nume = var1;
    }

    public void setDescriere(String var1) {
        this.descriere = var1;
    }

    public void setGen(GenLiterar var1) {
        this.gen = var1;
    }

    public String toString() {
        int var10000 = this.id;
        return "Sectiune{id=" + var10000 + ", nume='" + this.nume + "', gen=" + this.gen.getEticheta() + "}";
    }
}
