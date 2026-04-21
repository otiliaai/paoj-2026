package com.pao.laboratory08.exercise1;

public class Student implements Cloneable {
    private String nume;
    private int varsta;
    private Adresa adresa;

    Student(String nume, int varsta, Adresa adresa) {
        this.nume = nume;
        this.varsta = varsta;
        this.adresa = adresa;
    }

    public String getNume(){
        return this.nume;
    }

    public int getVarsta() {
        return this.varsta;
    }

    public Adresa getAdresa(){
        return this.adresa;
    }

    public void setNume(String nume){
        this.nume = nume;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public void setAdresa(Adresa adr){
        this.adresa = adr;
    }

    @Override
    public String toString(){
        return String.format("Student{nume='%s', varsta=%d, adresa=%s}", nume, varsta, adresa);
    }

    // clone() — implementare diferită pentru shallow vs. deep (vezi mai jos)
    public Student shallowclone() throws  CloneNotSupportedException{
        Student student_nou = (Student) super.clone();
        return student_nou;
    }

    public Student deepclone() throws  CloneNotSupportedException{
        Student student_nou = (Student) super.clone();
        student_nou.setAdresa((Adresa) this.adresa.clone());
        return student_nou;
    }
}