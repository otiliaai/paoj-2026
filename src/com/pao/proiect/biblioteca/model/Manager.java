package com.pao.proiect.biblioteca.model;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Angajat {
    private String departament;
    private List<Angajat> echipa;

    public Manager(String var1, String var2, String var3, double var4) {
        super(var1, var2, RolAngajat.MANAGER, var4);
        this.departament = var3;
        this.echipa = new ArrayList();
    }

    public String getDepartament() {
        return this.departament;
    }

    public List<Angajat> getEchipa() {
        return this.echipa;
    }

    public void setDepartament(String var1) {
        this.departament = var1;
    }

    public void adaugaAngajat(Angajat var1) {
        this.echipa.add(var1);
    }

    public String getRol() {
        return "Manager";
    }

    public String toString() {
        int var10000 = this.id;
        return "Manager{id=" + var10000 + ", numeComplet='" + this.numeComplet + "', email='" + this.email + "', departament='" + this.departament + "', nrAngajati=" + this.echipa.size() + ", salariu=" + this.getSalariu() + "}";
    }
}
