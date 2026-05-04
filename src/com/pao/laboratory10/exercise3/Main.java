package com.pao.laboratory10.exercise3;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/com/pao/laboratory10/exercise3/tranzactii.txt"));
        Scanner sc2 = new Scanner(System.in);
        ArrayList<Tranzactie> tranzactii = new ArrayList<>();

        while (sc.hasNextLine()) {
            String linie = sc.nextLine();
            String[] parts = linie.split(" ");
            int id = Integer.parseInt(parts[0]);
            double suma = Double.parseDouble(parts[1]);
            String data = parts[2];
            TipTranzactie tip = TipTranzactie.valueOf(parts[3]);

            tranzactii.add(new Tranzactie(id, suma, data, tip));
        }

        boolean running = true;

        while (running) {
            System.out.println("\n===== MENIU =====");
            System.out.println("1. Tranzactii CREDIT");
            System.out.println("2. Total procesat (suma tuturor)");
            System.out.println("3. Suma per luna");
            System.out.println("4. Top 3 tranzactii");
            System.out.println("5. Conturi sursa unice");
            System.out.println("6. Suma medie");
            System.out.println("7. Extras de cont per luna");
            System.out.println("0. Iesire");
            System.out.print("Alege optiunea: ");

            int optiune = Integer.parseInt(sc2.nextLine());

            switch (optiune) {
                case 1 -> {
                    System.out.println("\n--- Tranzactii CREDIT ---");
                    tranzactii.stream()
                            .filter(t -> t.getTip() == TipTranzactie.CREDIT)
                            .forEach(System.out::println);
                }
                case 2 -> {
                    System.out.println("\n--- Total procesat ---");
                    double total = tranzactii.stream()
                            .mapToDouble(Tranzactie::getSuma)
                            .sum();
                    System.out.printf(Locale.US, "Total: %.2f RON%n", total);
                }
                case 3 -> {
                    System.out.println("\n--- Suma per luna ---");
                    Map<String, Double> perLuna = tranzactii.stream()
                            .collect(Collectors.groupingBy(
                                    t -> t.getData().substring(0, 7),
                                    TreeMap::new,
                                    Collectors.summingDouble(Tranzactie::getSuma)
                            ));
                    for (var entry : perLuna.entrySet()) {
                        System.out.printf(Locale.US, "%s: %.2f RON%n",
                                entry.getKey(), entry.getValue());
                    }
                }
                case 4 -> {
                    System.out.println("\n--- Top 3 tranzactii ---");
                    List<Tranzactie> top = tranzactii.stream()
                            .sorted(Comparator.comparingDouble(Tranzactie::getSuma).reversed())
                            .limit(3)
                            .collect(Collectors.toList());
                    for (var t : top) {
                        System.out.println(t);
                    }
                }
                case 5 -> {
                    System.out.println("\n--- Conturi sursa unice ---");
                    List<String> conturi = tranzactii.stream()
                            .filter(t -> t instanceof TranzactieExtinsa)
                            .map(t -> ((TranzactieExtinsa) t).getContSursa())
                            .distinct()
                            .collect(Collectors.toList());
                    System.out.println("Conturi sursa unice: " + conturi);
                }
                case 6 -> {
                    System.out.println("\n--- Suma medie ---");
                    double medie = tranzactii.stream()
                            .mapToDouble(Tranzactie::getSuma)
                            .average()
                            .orElse(0.0);
                    System.out.printf(Locale.US, "Suma medie: %.2f RON%n", medie);
                }
                case 7 -> {
                    System.out.println("\n--- Extras de cont per luna ---");
                    Map<String, List<Tranzactie>> perLuna = tranzactii.stream()
                            .collect(Collectors.groupingBy(
                                    t -> t.getData().substring(0, 7),
                                    TreeMap::new,
                                    Collectors.toList()
                            ));
                    for (var entry : perLuna.entrySet()) {
                        String luna = entry.getKey();
                        List<Tranzactie> lista = entry.getValue();
                        double total = lista.stream()
                                .mapToDouble(Tranzactie::getSuma)
                                .sum();
                        System.out.printf(Locale.US,
                                "EXTRAS DE CONT - %s: %d tranzactii, total: %.2f RON%n",
                                luna, lista.size(), total);
                        for (var t : lista) {
                            System.out.println("  " + t);
                        }
                    }
                }
                case 0 -> {
                    System.out.println("La revedere!");
                    running = false;
                }
                default -> System.out.println("Optiune invalida.");
            }
        }
    }
}