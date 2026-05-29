package com.pao.proiect.biblioteca.exception;

public class CarteIndisponibilaException extends BibliotecaException {
    public CarteIndisponibilaException(String var1) {
        super("Cartea '" + var1 + "' nu este disponibila pentru imprumut.");
    }
}
