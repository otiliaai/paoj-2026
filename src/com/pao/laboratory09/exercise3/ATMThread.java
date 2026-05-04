// ATMThread.java
package com.pao.laboratory09.exercise3;

public class ATMThread extends Thread {
    private final int atmId;
    private final CoadaTranzactii coada;
    private static int contor = 1;

    public ATMThread(int atmId, CoadaTranzactii coada) {
        this.atmId = atmId;
        this.coada = coada;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            int id;
            synchronized (ATMThread.class) {
                id = contor++;
            }
            double suma = Math.round((100 + Math.random() * 900) * 100.0) / 100.0;
            Tranzactie t = new Tranzactie(id, suma, "2024-01-15", atmId);
            try {
                System.out.printf("[ATM-%d] trimite: Tranzactie #%d %.2f RON%n", atmId, id, suma);
                coada.adauga(t);
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}