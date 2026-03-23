package com.pao.laboratory05.audit;

/**
 * Exercise 4 (Bonus) — Audit Log
 *
 * Cerințele complete se află în:
 *   src/com/pao/laboratory05/Readme.md  →  secțiunea "Exercise 4 (Bonus) — Audit"
 *
 * Extinde soluția de la Exercise 3 cu un sistem de audit bazat pe record.
 * Creează fișierele de la zero în acest pachet, apoi rulează Main.java
 * pentru a verifica output-ul așteptat din Readme.
 */

import com.pao.laboratory05.angajati.Departament;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AngajatService inst = AngajatService.getInstance();

        while (true) {
            System.out.println("\n===== Gestionare Angajați =====");
            System.out.println("1. Adaugă angajat");
            System.out.println("2. Listare după salariu");
            System.out.println("3. Caută după departament");
            System.out.println("4. Afișează audit log");
            System.out.println("0. Ieșire");
            System.out.print("Opțiune: ");

            int op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1:
                    System.out.print("Nume: ");
                    String nume = sc.nextLine();

                    System.out.print("Departament (nume): ");
                    String dep = sc.nextLine();

                    System.out.print("Departament (locatie): ");
                    String loc = sc.nextLine();

                    com.pao.laboratory05.angajati.Departament departament = new Departament(dep, loc);

                    System.out.print("Salariu: ");
                    double sal = sc.nextDouble();
                    sc.nextLine();

                    com.pao.laboratory05.angajati.Angajat a = new com.pao.laboratory05.angajati.Angajat(nume, departament, sal);
                    inst.addAngajat(a);
                    break;

                case 2:
                    inst.listBySalary();
                    break;

                case 3:
                    System.out.print("Departament: ");
                    String d = sc.nextLine();
                    inst.findByDepartament(d);
                    break;

                case 4 :
                    System.out.println("--- Audit Log ---");
                    inst.printAuditLog();
                    break;

                case 0:
                    System.out.println("La revedere!");
                    return;

                default:
                    System.out.println("Opțiune invalidă.");
            }
        }
    }
}