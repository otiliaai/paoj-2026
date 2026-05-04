package com.pao.laboratory10.exercise1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // TODO: Implementează conform Readme.md
        //
        // Folosește LinkedList<Tranzactie> ca structură internă.
        // Citește comenzi din stdin până la EOF:
        //
        //   ENQUEUE id suma data tip   → addLast  (niciun output)
        //   DEQUEUE                    → removeFirst sau "Coada goala."
        //                                format: "Procesat: [id] data tip: suma RON"
        //   PUSH id suma data tip      → addFirst  (niciun output)
        //   POP                        → removeFirst sau "Coada goala."
        //                                format: "Extras: [id] data tip: suma RON"
        //   REMOVE_DEBIT               → Iterator.remove() pe toate DEBIT
        //                                afișează "Eliminat N tranzactii DEBIT."
        //   REMOVE_BELOW threshold     → Iterator.remove() pe suma < threshold
        //                                afișează "Eliminat N tranzactii sub threshold RON."
        //   PRINT                      → afișează toate, câte una pe linie
        //   SIZE                       → "Dimensiune coada: N"
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-10 CREDIT: 500.00 RON
        Scanner sc = new Scanner(System.in);
        LinkedList<Tranzactie> tranzactii = new LinkedList<>();
        while (sc.hasNextLine()){
            String comanda = sc.nextLine();
            String cmd = comanda.split(" ")[0];

            if (cmd.equals("ENQUEUE")){
                int id = Integer.parseInt(comanda.split(" ")[1]);
                double suma = Double.parseDouble(comanda.split(" ")[2]);
                String data = comanda.split(" ")[3];
                TipTranzactie tip = TipTranzactie.valueOf(comanda.split(" ")[4]);

                Tranzactie t = new Tranzactie(id,suma,data,tip);
                tranzactii.addLast(t);
            }
            else if (cmd.equals("DEQUEUE")){
                if (!tranzactii.isEmpty()) {
                    Tranzactie t = tranzactii.removeFirst();
                    System.out.println("Procesat: " + t);
                }
                else {
                    System.out.println("Coada goala.");
                }
            }
            else if (cmd.equals("PUSH")){
                int id = Integer.parseInt(comanda.split(" ")[1]);
                double suma = Double.parseDouble(comanda.split(" ")[2]);
                String data = comanda.split(" ")[3];
                TipTranzactie tip = TipTranzactie.valueOf(comanda.split(" ")[4]);

                Tranzactie t = new Tranzactie(id,suma,data,tip);
                tranzactii.addFirst(t);
            }
            else if (cmd.equals("POP")){
                if (!tranzactii.isEmpty()) {
                    Tranzactie t = tranzactii.removeFirst();
                    System.out.println("Extras: " + t);
                }
                else {
                    System.out.println("Coada goala.");
                }

            }

            else if (cmd.split("_")[0].equals("REMOVE")) {
                if (cmd.split("_")[1].equals("DEBIT")){
                    int nr = 0 ;
                    Iterator<Tranzactie> it = tranzactii.iterator();
                    while (it.hasNext()){
                        Tranzactie t = it.next();
                        if (t.tip.name().equals("DEBIT")) {
                            nr+=1;
                            it.remove();
                        }
                    }
                    System.out.println(String.format("Eliminat %d tranzactii DEBIT.",nr));
                }
                else {
                    double threshold = Double.parseDouble(comanda.split(" ")[1]);
                    int nr = 0 ;
                    Iterator<Tranzactie> it = tranzactii.iterator();
                    while (it.hasNext()){
                        Tranzactie t = it.next();
                        if (t.getSuma() < threshold) {
                            nr+=1;
                            it.remove();
                        }
                    }
                    System.out.println(String.format(Locale.US,"Eliminat %d tranzactii sub %.2f RON.",nr,threshold));

                }
            }
            else if (cmd.split(" ")[0].equals("PRINT")){
                for (var t : tranzactii) {
                    System.out.println(t);
                }
            }
            else if (cmd.split(" ")[0].equals("SIZE")) {
                System.out.println("Dimensiune coada: "+tranzactii.size());
            }
        }

    }
}
