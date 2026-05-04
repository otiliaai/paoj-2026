// CoadaTranzactii.java
package com.pao.laboratory09.exercise3;

import com.pao.laboratory09.exercise1.TipTranzactie;
import java.util.LinkedList;
import java.util.Queue;

public class CoadaTranzactii {
    private final Queue<Tranzactie> banda = new LinkedList<>();
    private final int capacitate = 5;

    public synchronized void adauga(Tranzactie t) throws InterruptedException {
        while (banda.size() == capacitate) {
            System.out.println("[ATM-" + t.atmId + "] astept loc...");
            wait();
        }
        banda.add(t);
        notifyAll();
    }

    public synchronized Tranzactie extrage() throws InterruptedException {
        while (banda.isEmpty()) {
            wait();
        }
        Tranzactie t = banda.poll();
        notifyAll();
        return t;
    }

    public synchronized boolean eGoala() {
        return banda.isEmpty();
    }
}