package com.pao.proiect.biblioteca.exception;

public class CarteNegasitaException extends BibliotecaException {
    public CarteNegasitaException(int var1) {
        super("Cartea cu id-ul " + var1 + " nu a fost gasita.");
    }
}
