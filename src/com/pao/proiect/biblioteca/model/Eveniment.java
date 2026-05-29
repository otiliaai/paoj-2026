package com.pao.proiect.biblioteca.model;

import java.util.ArrayList;
import java.util.List;

public class Eveniment {
    private final int id;
    private String titlu;
    private String data;
    private String locatie;
    private TipEveniment tip;
    private Autor autor;
    private List<Cititor> participanti;
    private static int contor = 1;

    public Eveniment(String var1, String var2, String var3, TipEveniment var4, Autor var5) {
        this.id = contor++;
        this.titlu = var1;
        this.data = var2;
        this.locatie = var3;
        this.tip = var4;
        this.autor = var5;
        this.participanti = new ArrayList();
    }

    public Eveniment(int id, String titlu, String data, String locatie, TipEveniment tip, Autor autor) {
        this.id = id;
        this.titlu = titlu;
        this.data = data;
        this.locatie = locatie;
        this.tip = tip;
        this.autor = autor;
        this.participanti = new ArrayList<>();

        if (id >= contor) {
            contor = id + 1;
        }
    }

    public void adaugaParticipant(Cititor var1) {
        this.participanti.add(var1);
    }

    public int getId() {
        return this.id;
    }

    public String getTitlu() {
        return this.titlu;
    }

    public String getData() {
        return this.data;
    }

    public String getLocatie() {
        return this.locatie;
    }

    public TipEveniment getTip() {
        return this.tip;
    }

    public Autor getAutor() {
        return this.autor;
    }

    public List<Cititor> getParticipanti() {
        return this.participanti;
    }

    public void setTitlu(String var1) {
        this.titlu = var1;
    }

    public void setData(String var1) {
        this.data = var1;
    }

    public void setLocatie(String var1) {
        this.locatie = var1;
    }

    public void setTip(TipEveniment var1) {
        this.tip = var1;
    }

    public void setAutor(Autor var1) {
        this.autor = var1;
    }

    public String toString() {
        String var1 = this.autor != null ? this.autor.getNumeComplet() : "N/A";
        int var10000 = this.id;
        return "Eveniment{id=" + var10000 + ", titlu='" + this.titlu + "', data='" + this.data + "', locatie='" + this.locatie + "', tip=" + this.tip.getDescriere() + ", autor='" + var1 + "', participanti=" + this.participanti.size() + "}";
    }
}
