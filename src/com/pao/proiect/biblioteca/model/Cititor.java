package com.pao.proiect.biblioteca.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cititor extends Persoana {
    private int nrCartiImprumutate = 0;
    private List<Imprumut> istoricImprumuturi = new ArrayList();
    private Abonament abonament;
    private static int contor = 1;

    public Cititor(String var1, String var2) {
        super(contor++, var1, var2);
    }

    public Cititor(int id, String numeComplet, String email, int numarCarti) {
        super(id, numeComplet, email);
        this.nrCartiImprumutate = numarCarti;
        this.istoricImprumuturi = new ArrayList<>();

        if (id >= contor) {
            contor = id + 1;
        }
    }

    public String getRol() {
        return "Cititor";
    }

    public int getNrCartiImprumutate() {
        return this.nrCartiImprumutate;
    }

    public List<Imprumut> getIstoric() {
        return this.istoricImprumuturi;
    }

    public Abonament getAbonament() {
        return this.abonament;
    }

    public void setNrCartiImprumutate(int var1) {
        this.nrCartiImprumutate = var1;
    }

    public void setAbonament(Abonament var1) {
        this.abonament = var1;
    }

    public void adaugaImprumut(Imprumut var1) {
        this.istoricImprumuturi.add(var1);
        ++this.nrCartiImprumutate;
    }

    public void returneazaCarte() {
        if (this.nrCartiImprumutate > 0) {
            --this.nrCartiImprumutate;
        }

    }

    public String toString() {
        return "Cititor{id=" + this.id + ", numeComplet='" + this.numeComplet + "', email='" + this.email + "', cartiImprumutate=" + this.nrCartiImprumutate + "}";
    }

    public boolean equals(Object var1) {
        if (this == var1) {
            return true;
        } else if (!(var1 instanceof Cititor)) {
            return false;
        } else {
            Cititor var2 = (Cititor)var1;
            return this.id == var2.id;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id});
    }
}
