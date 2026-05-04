// Tranzactie.java
package com.pao.laboratory09.exercise3;

public class Tranzactie {
    public int id;
    public double suma;
    public String data;
    public int atmId;

    public Tranzactie(int id, double suma, String data, int atmId) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.atmId = atmId;
    }
}