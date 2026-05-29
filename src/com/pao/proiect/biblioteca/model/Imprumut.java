package com.pao.proiect.biblioteca.model;

public class Imprumut {
    private final int id;
    private Cititor cititor;
    private Carte carte;
    private String dataImprumut;
    private String dataReturnare;
    private StatusImprumut status;
    private static int contor = 1;

    public Imprumut(Cititor var1, Carte var2, String var3) {
        this.id = contor++;
        this.cititor = var1;
        this.carte = var2;
        this.dataImprumut = var3;
        this.dataReturnare = null;
        this.status = StatusImprumut.ACTIV;
    }

    public int getId() {
        return this.id;
    }

    public Cititor getCititor() {
        return this.cititor;
    }

    public Carte getCarte() {
        return this.carte;
    }

    public String getDataImprumut() {
        return this.dataImprumut;
    }

    public String getDataReturnare() {
        return this.dataReturnare;
    }

    public StatusImprumut getStatus() {
        return this.status;
    }

    public void setCititor(Cititor var1) {
        this.cititor = var1;
    }

    public void setCarte(Carte var1) {
        this.carte = var1;
    }

    public void setDataImprumut(String var1) {
        this.dataImprumut = var1;
    }

    public void setDataReturnare(String var1) {
        this.dataReturnare = var1;
    }

    public void setStatus(StatusImprumut var1) {
        this.status = var1;
    }

    public String toString() {
        int var10000 = this.id;
        return "Imprumut{id=" + var10000 + ", cititor='" + this.cititor.getNumeComplet() + "', carte='" + this.carte.getTitlu() + "', dataImprumut='" + this.dataImprumut + "', dataReturnare='" + this.dataReturnare + "', status=" + this.status.getDescriere() + "}";
    }
}
