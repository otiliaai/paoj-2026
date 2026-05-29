package com.pao.proiect.biblioteca.model;

public class Abonament {
    private final int id;
    private TipAbonament tip;
    private String dataStart;
    private String dataExpirare;
    private boolean activ;
    private static int contor = 1;

    public Abonament(TipAbonament var1, String var2, String var3) {
        this.id = contor++;
        this.tip = var1;
        this.dataStart = var2;
        this.dataExpirare = var3;
        this.activ = true;
    }

    public Abonament(int id, TipAbonament tip, String dataStart, String dataExpirare, boolean activ) {
        this.id = id;
        this.tip = tip;
        this.dataStart = dataStart;
        this.dataExpirare = dataExpirare;
        this.activ = activ;

        if (id >= contor) {
            contor = id + 1;
        }
    }

    public boolean esteActiv() {
        return this.activ;
    }

    public int getId() {
        return this.id;
    }

    public TipAbonament getTip() {
        return this.tip;
    }

    public String getDataStart() {
        return this.dataStart;
    }

    public String getDataExpirare() {
        return this.dataExpirare;
    }

    public void setTip(TipAbonament var1) {
        this.tip = var1;
    }

    public void setDataStart(String var1) {
        this.dataStart = var1;
    }

    public void setDataExpirare(String var1) {
        this.dataExpirare = var1;
    }

    public void setActiv(boolean var1) {
        this.activ = var1;
    }

    public String toString() {
        int var10000 = this.id;
        return "Abonament{id=" + var10000 + ", tip=" + this.tip.getTitlu() + ", dataStart='" + this.dataStart + "', dataExpirare='" + this.dataExpirare + "', activ=" + this.activ + "}";
    }
}
