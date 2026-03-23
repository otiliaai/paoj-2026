package com.pao.laboratory05.angajati;

public class Angajat implements Comparable<Angajat>{
    private String nume;
    private Departament departament;
    private double salariu;

    Angajat(String nume, Departament departament, double salariu) {
        this.nume = nume;
        this.departament = departament;
        this.salariu = salariu;
    }

    public String getNume() {
        return this.nume;
    }

    public Departament getDepartament(){
        return this.departament;
    }

    public double getSalariu(){
        return this.salariu;
    }


    @Override
    public String toString() {
        return "Angajat{nume="+getNume()+",departament=" + getDepartament()+", salariu = "+getSalariu()+"}";
    }

    @Override
    public int compareTo(Angajat o) {
        return Double.compare( o.salariu, this.salariu);
    }
}
