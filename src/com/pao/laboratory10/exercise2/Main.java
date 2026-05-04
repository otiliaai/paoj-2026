package com.pao.laboratory10.exercise2;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        // TODO: Implementează conform Readme.md
        //
        // 1. Citește N din stdin, apoi cele N tranzacții (id suma data tip) — pot exista duplicate de id
        //    Stochează-le toate într-un ArrayList<Tranzactie> (cu duplicate, ordine inserare)
        //
        // 2. Procesează comenzile din stdin până la EOF:
        //
        //   UNIQUE_IDS      → LinkedHashSet<Integer> cu id-urile în ordinea primei apariții
        //                     afișează: "IDs unice (N): [1, 2, 3, ...]"
        //
        //   MONTHLY_REPORT  → TreeMap<String, ...> grupat pe yyyy-MM (substring 0-7 din data)
        //                     pentru fiecare lună, sumele CREDIT și DEBIT
        //                     format: "yyyy-MM: CREDIT X.XX RON, DEBIT Y.YY RON"
        //
        //   TOP n           → primele n tranzacții după suma descrescătoare (nu modifică lista)
        //                     afișează "Top n:" urmat de n linii
        //
        //   SORT_ASC        → Collections.sort cu suma crescătoare; afișează lista sortată
        //   SORT_DESC       → Collections.sort cu suma descrescătoare; afișează lista sortată
        //   REVERSE         → Collections.reverse; afișează lista
        //   MIN_MAX         → Collections.min/max după suma
        //                     "MIN: [id] data tip: suma RON"
        //                     "MAX: [id] data tip: suma RON"
        //
        //   CME_DEMO        → încearcă for(t : lista) lista.remove(t) în try-catch
        //                     afișează "ConcurrentModificationException prins: modificare in iteratie detectata."
        //
        // Format linie tranzacție: [id] data tip: suma RON
        //   Ex: [1] 2024-01-15 CREDIT: 1500.00 RON

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        ArrayList<Tranzactie> tr_duplicate = new ArrayList<>();

        for (int i = 0; i< n ; i++) {
            String linie = sc.nextLine();
            int id = Integer.parseInt(linie.split(" ")[0]);
            double suma  = Double.parseDouble(linie.split(" ")[1]);
            String data = linie.split(" ")[2];
            TipTranzactie tip = TipTranzactie.valueOf(linie.split(" ")[3]);

            Tranzactie tranzactie = new Tranzactie(id,suma,data,tip);
            tr_duplicate.add(tranzactie);
        }

        while(sc.hasNext()) {
            String comanda = sc.nextLine();

            if (comanda.split("_")[0].equals("UNIQUE")){
                LinkedHashSet<Integer> tranzactii = new LinkedHashSet<>();

                for(var t : tr_duplicate) {
                    tranzactii.add(t.getId());
                }

                System.out.println("IDs unice "+"("+tranzactii.size()+"): " + tranzactii  );
            }

            else if (comanda.split("_")[0].equals("MONTHLY")){
                TreeMap<String, double[]> lunar = new TreeMap<>();

                for (var t : tr_duplicate) {
                    String cheie = t.getData().substring(0,7);
                    if (!lunar.containsKey(cheie)) {
                        lunar.put(cheie, new double[2]);
                    }
                    if (t.getTip().equals(TipTranzactie.CREDIT))
                    {
                        lunar.get(cheie)[0] += t.getSuma();
                    }
                    else  {
                        lunar.get(cheie)[1] += t.getSuma();
                    }
                }

                for (var entry : lunar.entrySet()) {
                    System.out.printf(Locale.US, "%s: CREDIT %.2f RON, DEBIT %.2f RON%n",
                            entry.getKey(), entry.getValue()[0], entry.getValue()[1]
                    );
                }

            }

            else if (comanda.split("_")[0].equals("TOP")){
                ArrayList<Tranzactie> copie = new ArrayList<>(tr_duplicate);
                Collections.sort(copie, Comparator.comparingDouble(Tranzactie::getSuma).reversed());

                int n_top = Integer.parseInt(comanda.split(" ")[1]);
                List<Tranzactie> top = copie.subList(0, n_top);

                System.out.println("Top " + n_top + ":");
                for (var t : top) {
                    System.out.println(t);
                }
            }

            else if (comanda.equals("SORT_ASC")){
                Collections.sort(tr_duplicate, Comparator.comparingDouble(Tranzactie::getSuma));

                for (var t : tr_duplicate) {
                    System.out.println(t);
                }
            }

            else if (comanda.equals("SORT_DESC")) {
                Collections.sort(tr_duplicate, Comparator.comparingDouble(Tranzactie::getSuma).reversed());

                for (var t : tr_duplicate) {
                    System.out.println(t);
                }
            }
            else if (comanda.equals("REVERSE")){
                Collections.reverse(tr_duplicate);

                for (var t : tr_duplicate) {
                    System.out.println(t);
                }
            }

            else if (comanda.equals("MIN_MAX")){
                Tranzactie min = Collections.min(tr_duplicate, Comparator.comparingDouble(Tranzactie::getSuma));
                Tranzactie max = Collections.max(tr_duplicate, Comparator.comparingDouble(Tranzactie::getSuma));

                System.out.println("MIN: " + min);
                System.out.println("MAX: " + max);
            }

            else if (comanda.equals("CME_DEMO")) {
                try {
                    for (Tranzactie t : tr_duplicate)
                        tr_duplicate.remove(t);
                }
                catch (ConcurrentModificationException e) {
                    System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata.");
                }
            }
        }

    }
}
