
package com.pao.proiect.biblioteca.exception;

public class ImprumutNegasitException extends BibliotecaException {
    public ImprumutNegasitException(int var1) {
        super("Imprumutul cu id-ul " + var1 + " nu a fost gasit.");
    }
}
