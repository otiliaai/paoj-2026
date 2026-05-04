// ProcessorThread.java
package com.pao.laboratory09.exercise3;

public class ProcessorThread implements Runnable {
    public volatile boolean activ = true;
    private final CoadaTranzactii coada;
    private int total = 0;

    public ProcessorThread(CoadaTranzactii coada) {
        this.coada = coada;
    }

    @Override
    public void run() {
        while (activ || !coada.eGoala()) {
            try {
                Tranzactie t = coada.extrage();
                total++;
                System.out.printf("[Processor] Factura #%d - %.2f RON | %s%n",
                        t.id, t.suma, t.data);
                Thread.sleep(80);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public int getTotal() {
        return total;
    }
}