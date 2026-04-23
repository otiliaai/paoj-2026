package biblioteca;

import biblioteca.exception.CarteIndisponibilaException;
import biblioteca.exception.CititorNegasitException;
import biblioteca.service.CarteService;
import biblioteca.service.CititorService;

import java.util.Scanner;

import biblioteca.model.*;

public class Main {
    public static void main(String[] args) {
        CarteService carte_s = CarteService.getInstance();
        CititorService cititor_s = CititorService.getInstance();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== BIBLIOTECA ===");
            System.out.println("1. Adauga carte");
            System.out.println("2. Sterge carte");
            System.out.println("3. Inregistreaza cititor");
            System.out.println("4. Sterge cititor");
            System.out.println("5. Imprumuta carte");
            System.out.println("6. Returneaza carte");
            System.out.println("7. Cauta carti dupa autor");
            System.out.println("8. Cauta carte dupa titlu");
            System.out.println("9. Listeaza carti dupa sectiune");
            System.out.println("10. Afiseaza carti disponibile");
            System.out.println("11. Istoric imprumuturi cititor");
            System.out.println("0. Iesire");
            System.out.print("Alege optiunea: ");

            int optiune = scanner.nextInt();
            scanner.nextLine();

            switch (optiune) {
                case 1 -> {
                    System.out.print("Titlu: ");
                    String titlu = scanner.nextLine();
                    System.out.print("Nume autor: ");
                    String numeAutor = scanner.nextLine();
                    System.out.print("Nationalitate autor: ");
                    String nationalitate = scanner.nextLine();
                    System.out.print("Nume sectiune: ");
                    String numeSectiune = scanner.nextLine();
                    System.out.print("Cod ISBN: ");
                    String codISBN = scanner.nextLine();

                    Autor autor = new Autor(numeAutor, nationalitate);
                    Sectiune sectiune = new Sectiune(numeSectiune, "");
                    ISBN isbn = new ISBN(codISBN, "Romania", 2024);
                    Carte carte = new Carte(titlu, autor, sectiune, isbn);
                    carte_s.adaugaCarte(carte);
                    System.out.println("Carte adaugata!");
                }
                case 2 -> {
                    System.out.print("Id carte de sters: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    carte_s.stergeCarte(id);
                    System.out.println("Carte stearsa!");
                }
                case 3 -> {
                    System.out.print("Nume: ");
                    String nume = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    Cititor cititor = new Cititor(nume,email);
                    cititor_s.adaugaCititor(cititor);
                    System.out.println("Cititor adaugat: "+ cititor);
                }
                case 4 -> {
                    System.out.print("Id cititor de sters: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        cititor_s.stergeCititor(id);
                        System.out.println("Cititor sters!");
                    } catch (CititorNegasitException e) {
                        System.out.println("Eroare: " + e.getMessage());
                    }
                }
                case 5 -> {
                    System.out.print("Id cititor: ");
                    int idCititor = scanner.nextInt();
                    System.out.print("Id carte: ");
                    int idCarte = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Data imprumut (ex: 2024-04-22): ");
                    String data = scanner.nextLine();

                    Cititor cititor = cititor_s.cautaDupaId(idCititor);
                    Carte carte = carte_s.cautaDupaId(idCarte);

                    if (cititor == null) {
                        System.out.println("Cititorul nu exista!");
                    } else if (carte == null) {
                        System.out.println("Cartea nu exista!");
                    } else {
                        try {
                            cititor_s.ImprumutaCarte(carte, cititor, data);
                            System.out.println("Carte imprumutata!");
                        } catch (CarteIndisponibilaException e) {
                            System.out.println("Eroare: " + e.getMessage());
                        }
                    }
                }
                case 6 -> {
                    System.out.print("Id imprumut: ");
                    int idImprumut = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Data returnare (ex: 2024-04-22): ");
                    String data = scanner.nextLine();

                    cititor_s.returneazaCarte(idImprumut, data);
                    System.out.println("Carte returnata!");
                }
                case 7 -> {
                    System.out.print("Nume autor: ");
                    String numeAutor = scanner.nextLine();
                    carte_s.cautaDupaAutor(numeAutor).forEach(System.out::println);
                }
                case 8 -> {
                    System.out.print("Titlu carte: ");
                    String titlu = scanner.nextLine();
                    Carte carte = carte_s.cautaDupaTitlu(titlu);
                    if (carte == null)
                        System.out.println("Cartea nu exista!");
                    else
                        System.out.println(carte);
                }
                case 9 -> {
                    System.out.print("Nume sectiune: ");
                    String numeSectiune = scanner.nextLine();
                    carte_s.listeazaDupaSectiune(numeSectiune);
                }
                case 10 -> {
                    carte_s.listeazaDisponibile();
                }
                case 11 -> {
                    System.out.print("Id cititor: ");
                    int idCititor = scanner.nextInt();
                    scanner.nextLine();
                    cititor_s.listeaza_istoric(idCititor);
                }
                case 0 -> {
                    System.out.println("Terminat");
                    return;
                }
                default -> System.out.println("Optiune invalida!");
            }
        }
    }
}