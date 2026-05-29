package com.pao.proiect.biblioteca.model;

import com.pao.proiect.biblioteca.util.Displayable;

public abstract class Persoana implements Displayable {
    protected int id;
    protected String numeComplet;
    protected String email;

    public Persoana(int var1, String var2, String var3) {
        this.id = var1;
        this.numeComplet = var2;
        this.email = var3;
    }

    public int getId() {
        return this.id;
    }

    public String getNumeComplet() {
        return this.numeComplet;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String var1) {
        this.email = var1;
    }

    public abstract String getRol();

    public String toString() {
        int var10000 = this.id;
        return "Persoana{id=" + var10000 + ", numeComplet='" + this.numeComplet + "', email='" + this.email + "', rol='" + this.getRol() + "'}";
    }

    public void afiseaza() {
        System.out.println(this);
    }
}
