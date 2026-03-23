package com.pao.laboratory05.angajati;

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

                    Departament departament = new Departament(dep, loc);

                    System.out.print("Salariu: ");
                    double sal = sc.nextDouble();
                    sc.nextLine();

                    Angajat a = new Angajat(nume, departament, sal);
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

                case 0:
                    System.out.println("La revedere!");
                    return;

                default:
                    System.out.println("Opțiune invalidă.");
            }
        }
    }
}