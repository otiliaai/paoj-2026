package com.pao.proiect.biblioteca.model;

public class Angajat extends Persoana {
    private RolAngajat rol;
    private double salariu;
    private static int contor = 1;

    public Angajat(String var1, String var2, RolAngajat var3, double var4) {
        super(contor++, var1, var2);
        this.rol = var3;
        this.salariu = var4;
    }

    public RolAngajat getRolAngajat() {
        return this.rol;
    }

    public double getSalariu() {
        return this.salariu;
    }

    public void setRol(RolAngajat var1) {
        this.rol = var1;
    }

    public void setSalariu(double var1) {
        this.salariu = var1;
    }

    public String getRol() {
        return this.rol.getTitlu();
    }

    public String toString() {
        int var10000 = this.id;
        return "Angajat{id=" + var10000 + ", numeComplet='" + this.numeComplet + "', email='" + this.email + "', rol=" + this.rol.getTitlu() + ", salariu=" + this.salariu + "}";
    }
}
