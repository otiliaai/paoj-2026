package com.pao.laboratory05.angajati;

import java.util.Arrays;

public class AngajatService {
    private static AngajatService instance;
    private Angajat[] angajati;

    private AngajatService(){
        angajati = new Angajat[0];
    }

    public static AngajatService getInstance(){
        if (instance == null)
            return new AngajatService();
        return instance;
    }

    void addAngajat(Angajat a) {
        Angajat[]  new_angajati = new Angajat[angajati.length+1];
        System.arraycopy(angajati,0,new_angajati,0,angajati.length);
        new_angajati[angajati.length] = a;
        angajati = new_angajati;
        System.out.println("Angajat adaugat: "+a.getNume());
    }

    void printAll() {
        for (var a:angajati) {
            System.out.println((a));
        }
    }

    void listBySalary() {
        Angajat[] copy = angajati.clone();
        Arrays.sort(copy);
        for (var a:copy) {
            System.out.println((a));
        }
    }

    void  findByDepartament(String numeDept){
        int cnt = 0;
        for (var a : angajati) {
            if (a.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                cnt +=1;
                System.out.println(a);
            }
        }
        if (cnt == 0) {
            System.out.println("Niciun angajat în departamentul: " + numeDept);
        }
    }
}
