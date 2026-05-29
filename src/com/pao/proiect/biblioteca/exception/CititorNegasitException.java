package com.pao.proiect.biblioteca.exception;

public class CititorNegasitException extends BibliotecaException {
    public CititorNegasitException(int var1) {

        super("Cititorul cu id-ul " + var1 + " nu a fost gasit.");
    }
}
