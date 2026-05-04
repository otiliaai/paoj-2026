package com.pao.laboratory09.exercise1;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class Tranzactie implements Serializable {

    private static final long serialVersionUID = 1L;

    protected int id;
    protected double suma;
    protected String data;
    protected String contSursa;
    protected String contDestinatie;
    protected TipTranzactie tip;
    protected transient String note;


    public Tranzactie(int id, double suma, String data, String contSursa, String contDestinatie, TipTranzactie tip) {
        this.id = id;
        this.suma = suma;
        this.data = data;
        this.contSursa = contSursa;
        this.contDestinatie = contDestinatie;
        this.tip = tip;
    }

    //inainte de serializare
    private void writeObject(ObjectOutputStream out) throws IOException {
        this.note = "procesat";
        out.defaultWriteObject();
    }

    // dupa deserializare
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

    @Override
    public String toString() {
        return "Tranzactie{" + "id=" + id + ", suma=" + suma + ", data='"+ data + '\'' + ", contSursa='" + contSursa + '\'' + ", " +
                "contDestinatie='" + contDestinatie + '\'' + ", tip=" + tip + ", note='" + note + '\'' + '}';
    }
}