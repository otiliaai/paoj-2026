package com.pao.proiect.biblioteca.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Autor extends Persoana {
    private String nationalitate;
    private List<Carte> cartiScrise;
    private static int contor = 1;

    public Autor(String var1, String var2) {
        super(contor++, var1, "");
        this.nationalitate = var2;
        this.cartiScrise = new ArrayList();
    }

    public Autor(int id, String numeComplet, String nationalitate) {
        super(id, numeComplet, "");
        this.nationalitate = nationalitate;
        this.cartiScrise = new ArrayList<>();

        if (id >= contor) {
            contor = id + 1;
        }
    }

    public String getNationalitate() {
        return this.nationalitate;
    }

    public List<Carte> getCartiScrise() {
        return this.cartiScrise;
    }

    public void setNationalitate(String var1) {
        this.nationalitate = var1;
    }

    public void adaugaCarte(Carte var1) {
        this.cartiScrise.add(var1);
    }

    public String getRol() {
        return "Autor";
    }

    public String toString() {
        return "Autor{id=" + this.id + ", numeComplet='" + this.numeComplet + "', nationalitate='" + this.nationalitate + "'}";
    }

    public boolean equals(Object var1) {
        if (this == var1) {
            return true;
        } else if (!(var1 instanceof Autor)) {
            return false;
        } else {
            Autor var2 = (Autor)var1;
            return this.id == var2.id;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id});
    }
}
