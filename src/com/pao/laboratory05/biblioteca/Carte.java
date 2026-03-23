package com.pao.laboratory05.biblioteca;

public class Carte implements Comparable<Carte>{
    private String titlu;
    private String autor;
    private int an;
    private double rating;

//    Constructor complet
//    Getteri pentru toate câmpurile
//    toString() → "Carte{titlu='...', autor='...', an=..., rating=...}"
//            implements Comparable<Carte> — sortare după rating descrescător (cel mai bine cotat apare primul)
    Carte( String titlu, String autor, int an, double rating) {
        this.titlu = titlu;
        this.autor = autor;
        this.an = an;
        this.rating = rating;
    }

    public String getTitlu() {
        return titlu;
    }
    public String getAutor() {
        return this.autor;
    }
    public int getAn(){
        return this.an;
    }
    public double getRating() {
        return this.rating;
    }

    @Override
    public String toString() {
        return "Carte{titlu="+getTitlu()+", autor="+getAutor()+", an=" + getAn() +" , rating=" + getRating()+"}";
    }

    @Override
    public int compareTo(Carte o) {
        return Double.compare(o.rating, this.rating);
    }
}
