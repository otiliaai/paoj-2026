package com.pao.proiect.biblioteca.model;

public enum TipAbonament {
    LUNAR("Lunar", (double)20.0F),
    ANUAL("Anual", (double)15.0F),
    STUDENT("Student", (double)10.0F),
    SENIOR("Senior", (double)8.0F);

    private final String titlu;
    private final double pretLuna;

    private TipAbonament(String var3, double var4) {
        this.titlu = var3;
        this.pretLuna = var4;
    }

    public String getTitlu() {
        return this.titlu;
    }

    public double getPretLuna() {
        return this.pretLuna;
    }
}
