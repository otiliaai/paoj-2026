package com.pao.proiect.biblioteca.exception;

public class LimitaImprumutDepasitaException extends BibliotecaException {
    public LimitaImprumutDepasitaException(int var1) {
        super("Limita maxima de " + var1 + " carti imprumutate simultan a fost depasita.");
    }
}
