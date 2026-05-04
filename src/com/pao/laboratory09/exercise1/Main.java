package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());
        List<Tranzactie> tranzactii = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String[] el_linie = sc.nextLine().split(" ");
            int id = Integer.parseInt(el_linie[0]);
            double suma = Double.parseDouble(el_linie[1]);
            String data = el_linie[2];
            String contSursa = el_linie[3];
            String contDestinatie = el_linie[4];
            TipTranzactie tip = TipTranzactie.valueOf(el_linie[5]);
            Tranzactie t = new Tranzactie(id, suma, data, contSursa, contDestinatie, tip);
            t.note = "procesat";
            tranzactii.add(t);
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            oos.writeObject(tranzactii);
        }

        List<Tranzactie> listaDeserializata;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(OUTPUT_FILE))) {
            listaDeserializata = (List<Tranzactie>) ois.readObject();
        }

        while (sc.hasNextLine()) {
            String linie = sc.nextLine().trim();
            String[] parts = linie.split(" ");
            String comanda = parts[0];

            if (comanda.equals("LIST")) {
                for (Tranzactie t : listaDeserializata) {
                    System.out.printf(Locale.US, "[%d] %s %s: %.2f RON | %s -> %s%n",
                            t.id, t.data, t.tip, t.suma, t.contSursa, t.contDestinatie);}

            } else if (comanda.equals("FILTER")) {
                String prefix = parts[1];
                List<Tranzactie> rezultate = new ArrayList<>();
                for (Tranzactie t : listaDeserializata) {
                    if (t.data.startsWith(prefix)) {
                        rezultate.add(t);
                    }
                }
                if (rezultate.isEmpty()) {
                    System.out.println("Niciun rezultat.");
                } else {
                    for (Tranzactie t : rezultate) {
                        System.out.printf(Locale.US, "[%d] %s %s: %.2f RON | %s -> %s%n",
                                t.id, t.data, t.tip, t.suma, t.contSursa, t.contDestinatie);
                    }
                }

            } else if (comanda.equals("NOTE")) {
                int cautId = Integer.parseInt(parts[1]);
                Tranzactie gasit = null;
                for (Tranzactie t : listaDeserializata) {
                    if (t.id == cautId) {
                        gasit = t;
                        break;
                    }
                }
                if (gasit == null) {
                    System.out.println("NOTE[" + cautId + "]: not found");
                } else {
                    System.out.println("NOTE[" + cautId + "]: " + gasit.note);
                }
            }
        }
    }
}