package com.pao.laboratory10.exercise1;

public class Tranzactie {
    protected int id;
    protected double suma;
    protected String data;
    protected TipTranzactie tip;

    public Tranzactie(int id, double suma, String data, TipTranzactie tip) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.tip = tip;
    }

    public int getId(){
        return this.id;
    }

    public double getSuma(){
        return this.suma;
    }

    public String getData(){
        return this.data;
    }

    public TipTranzactie getTip() {
        return tip;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public void setTip(TipTranzactie tip) {
        this.tip = tip;
    }

    @Override
    public String toString(){
        return String.format(java.util.Locale.US, "[%d] %s %s: %.2f RON", id, data, tip, suma);
    }
}
