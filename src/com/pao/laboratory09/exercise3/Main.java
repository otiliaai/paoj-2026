// Main.java
package com.pao.laboratory09.exercise3;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CoadaTranzactii coada = new CoadaTranzactii();
        ProcessorThread processorThread = new ProcessorThread(coada);

        // 1. Creare ATM-uri
        ATMThread atm1 = new ATMThread(1, coada);
        ATMThread atm2 = new ATMThread(2, coada);
        ATMThread atm3 = new ATMThread(3, coada);

        // 2. Pornire consumator
        Thread firProcessor = new Thread(processorThread);
        firProcessor.start();

        // 3. Pornire producători
        atm1.start();
        atm2.start();
        atm3.start();

        // 4. Așteptare terminare producători
        atm1.join();
        atm2.join();
        atm3.join();

        // 5. Oprire gracioasă consumator
        processorThread.activ = false;
        synchronized (coada) {
            coada.notifyAll();
        }

        // 6. Așteptare terminare consumator
        firProcessor.join();

        // 7. Final
        System.out.println("Toate tranzactiile procesate. Total: " + processorThread.getTotal());
    }
}