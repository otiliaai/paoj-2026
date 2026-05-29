package com.pao.proiect.biblioteca.model;

import com.pao.proiect.biblioteca.util.Displayable;
import java.util.Objects;

public class Carte implements Comparable<Carte>, Displayable {
    private final int id;
    private String titlu;
    private Autor autor;
    private Sectiune sectiune;
    private ISBN isbn;
    private boolean disponibilitate;
    private static int contor = 1;

    public Carte(String var1, Autor var2, Sectiune var3, ISBN var4) {
        this.id = contor++;
        this.titlu = var1;
        this.autor = var2;
        this.sectiune = var3;
        this.isbn = var4;
        this.disponibilitate = true;
    }

    public Carte(int var1, String var2, Autor var3, Sectiune var4, ISBN var5) {
        this.id = var1;
        this.titlu = var2;
        this.autor = var3;
        this.sectiune = var4;
        this.isbn = var5;
        this.disponibilitate = true;
        if (var1 >= contor) {
            contor = var1 + 1;
        }

    }

    public int getId() {
        return this.id;
    }

    public String getTitlu() {
        return this.titlu;
    }

    public Autor getAutor() {
        return this.autor;
    }

    public Sectiune getSectiune() {
        return this.sectiune;
    }

    public ISBN getIsbn() {
        return this.isbn;
    }

    public boolean getDisponibilitate() {
        return this.disponibilitate;
    }

    public void setTitlu(String var1) {
        this.titlu = var1;
    }

    public void setAutor(Autor var1) {
        this.autor = var1;
    }

    public void setSectiune(Sectiune var1) {
        this.sectiune = var1;
    }

    public void setIsbn(ISBN var1) {
        this.isbn = var1;
    }

    public void setDisponibilitate(boolean var1) {
        this.disponibilitate = var1;
    }

    public int compareTo(Carte var1) {
        return this.titlu.compareToIgnoreCase(var1.titlu);
    }

    public void afiseaza() {
        System.out.println(this);
    }

    public String toString() {
        int var10000 = this.id;
        return "Carte{id=" + var10000 + ", titlu='" + this.titlu + "', autor='" + this.autor.getNumeComplet() + "', sectiune='" + this.sectiune.getNume() + "', isbn=" + this.isbn.getCod() + ", disponibila=" + this.disponibilitate + "}";
    }

    public boolean equals(Object var1) {
        if (this == var1) {
            return true;
        } else if (!(var1 instanceof Carte)) {
            return false;
        } else {
            Carte var2 = (Carte)var1;
            return this.id == var2.id;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.id});
    }
}
