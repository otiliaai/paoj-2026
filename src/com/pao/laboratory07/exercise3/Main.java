package com.pao.laboratory07.exercise3;
import com.pao.laboratory07.exercise1.StareComanda;
import com.pao.laboratory07.exercise2.Comanda;
import com.pao.laboratory07.exercise2.ComandaGratuita;
import com.pao.laboratory07.exercise2.ComandaRedusa;
import com.pao.laboratory07.exercise2.ComandaStandard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //citire N
        int n = Integer.parseInt(sc.nextLine().trim());
        List<Comanda> comenzi = new ArrayList<>();
        int nrStandard = 0, nrDiscounted = 0, nrGift = 0;
        double sumaStandard = 0, sumaDiscounted = 0;
        // STANDARD: <nume>, pret: X.XX lei [PLACED] - client: <client>
        for (int i = 0; i < n; i++) {

            String line = sc.nextLine().trim();
            String[] tokens = line.split(" ");
            if (tokens[0].equals("STANDARD")) {
                String nume = tokens[1];
                double pret = Double.parseDouble(tokens[2]);
                String client = tokens[3];
                Comanda c = new ComandaStandard(nume, pret,client);
                comenzi.add(c);
                nrStandard++;
                sumaStandard += c.pretFinal();
            }
            else if (tokens[0].equals("DISCOUNTED")) {
                String nume = tokens[1];
                double pret = Double.parseDouble(tokens[2]);
                int discount = Integer.parseInt(tokens[3]);
                String client = tokens[4];
                Comanda c = new ComandaRedusa(nume, pret, discount,client);
                comenzi.add(c);
                nrDiscounted++;
                sumaDiscounted += c.pretFinal();
            } else if (tokens[0].equals("GIFT")) {
                String nume = tokens[1];
                String client = tokens[2];
                Comanda c = new ComandaGratuita(nume,0,client);
                comenzi.add(c);
                nrGift++;
            }
           // System.out.println(Arrays.toString(tokens));
        }
        for (Comanda c : comenzi) {
            System.out.println(c.descriere() + " - client: " + c.getClient());
        }
        System.out.println();

        while (true) {
            String comanda_totala =  sc.nextLine().trim();
            String comanda = comanda_totala.split(" ")[0];
            switch (comanda) {
                case "STATS" -> {
                    System.out.println();
                    System.out.println("STANDARD: medie = " + String.format("%.2f",sumaStandard/nrStandard)+ " lei ");
                    System.out.println("DISCOUNTED: medie = " + String.format("%.2f",sumaDiscounted/nrDiscounted)+ " lei ");
                    System.out.println("STANDARD: medie = 0.00 lei ");
                }
                case "FILTER" -> {
                    System.out.println();
                    int nr = Integer.parseInt(comanda_totala.split(" ")[1]);
//                    System.out.println(nr);
                    for (var c : comenzi) {
                        if (c.pretFinal() > nr) {
                            System.out.println(c.descriere() + " - client: " + c.getClient());
                        }
                    }
                }
                case "SORT" -> {
                    System.out.println();
                    comenzi.stream()
                            .sorted((a, b) -> {
                                int cmp = a.getClient().compareTo(b.getClient());
                                if (cmp != 0) return cmp; // clienti diferiti -> alfabetic
                                return Double.compare(a.pretFinal(), b.pretFinal()); // acelasi client -> pret crescator
                            })
                            .forEach(c -> System.out.println(c.descriere() + " - client: " + c.getClient()));
                }
                case "SPECIAL" -> {
                    System.out.println();
                    for (var c : comenzi) {
                        if ( c instanceof  ComandaRedusa) {
                            if (((ComandaRedusa) c).getDiscountProcent() > 15 ) {
                                System.out.println(c.descriere() + " - client: " + c.getClient());
                            }
                        }
                    }
                }
                case "QUIT" ->
                {
                    System.out.println();
                    System.out.println("Parasire program!");
                    return;
                }
            }
        }
    }
}
