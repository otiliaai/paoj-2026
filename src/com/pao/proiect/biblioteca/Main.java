package com.pao.proiect.biblioteca;

import com.pao.proiect.biblioteca.exception.CarteIndisponibilaException;
import com.pao.proiect.biblioteca.exception.CititorNegasitException;
import com.pao.proiect.biblioteca.model.Abonament;
import com.pao.proiect.biblioteca.model.Angajat;
import com.pao.proiect.biblioteca.model.Autor;
import com.pao.proiect.biblioteca.model.Carte;
import com.pao.proiect.biblioteca.model.Cititor;
import com.pao.proiect.biblioteca.model.Eveniment;
import com.pao.proiect.biblioteca.model.GenLiterar;
import com.pao.proiect.biblioteca.model.ISBN;
import com.pao.proiect.biblioteca.model.Imprumut;
import com.pao.proiect.biblioteca.model.Manager;
import com.pao.proiect.biblioteca.model.RolAngajat;
import com.pao.proiect.biblioteca.model.Sectiune;
import com.pao.proiect.biblioteca.model.TipAbonament;
import com.pao.proiect.biblioteca.model.TipEveniment;
import com.pao.proiect.biblioteca.service.CarteService;
import com.pao.proiect.biblioteca.service.CititorService;

import java.util.List;

public class Main {
    private static final String SEP = "=".repeat(60);

    public static void main(String[] args) {
        CarteService carteService = CarteService.getInstance();
        CititorService cititorService = CititorService.getInstance();

        printSection("1. ADAUGARE CARTI");

        Autor autorEminescu = new Autor("Mihai Eminescu", "Romana");
        Autor autorMarquez = new Autor("Gabriel Garcia Marquez", "Columbiana");
        Autor autorHerbert = new Autor("Frank Herbert", "Americana");

        Sectiune sectiuneRomane = new Sectiune(
                "Romane Clasice",
                "Capodopere ale literaturii universale",
                GenLiterar.ROMAN
        );

        Sectiune sectiuneSf = new Sectiune(
                "Fantezie & SF",
                "Carti de fantezie si stiinta fictiune",
                GenLiterar.FANTEZIE
        );

        Sectiune sectiunePoezie = new Sectiune(
                "Poezie",
                "Colectia de poezie romaneasca si universala",
                GenLiterar.POEZIE
        );

        ISBN isbnLuceafarul = new ISBN("978-973-46-0001", "RO", 1883);
        ISBN isbnMarquez = new ISBN("978-0-06-088328-7", "CO", 1967);
        ISBN isbnDune = new ISBN("978-0-441-01356-5", "US", 1965);

        Carte carteLuceafarul = new Carte("Luceafarul", autorEminescu, sectiunePoezie, isbnLuceafarul);
        Carte carteMarquez = new Carte("Un veac de singuratate", autorMarquez, sectiuneRomane, isbnMarquez);
        Carte carteDune = new Carte("Dune", autorHerbert, sectiuneSf, isbnDune);

        autorEminescu.adaugaCarte(carteLuceafarul);
        autorMarquez.adaugaCarte(carteMarquez);
        autorHerbert.adaugaCarte(carteDune);

        sectiunePoezie.adaugaCarte(carteLuceafarul);
        sectiuneRomane.adaugaCarte(carteMarquez);
        sectiuneSf.adaugaCarte(carteDune);

        carteService.adaugaCarte(carteLuceafarul);
        carteService.adaugaCarte(carteMarquez);
        carteService.adaugaCarte(carteDune);

        System.out.println("Carti adaugate:");
        carteLuceafarul.afiseaza();
        carteMarquez.afiseaza();
        carteDune.afiseaza();

        printSection("2. ADAUGARE CITITORI CU ABONAMENTE");

        Cititor cititorAna = new Cititor("Ana Popescu", "ana.popescu@email.ro");
        Cititor cititorIon = new Cititor("Ion Ionescu", "ion.ionescu@email.ro");
        Cititor cititorMaria = new Cititor("Maria Constantin", "maria.c@student.ro");

        cititorAna.setAbonament(new Abonament(TipAbonament.ANUAL, "2026-01-01", "2026-12-31"));
        cititorIon.setAbonament(new Abonament(TipAbonament.LUNAR, "2026-05-01", "2026-05-31"));
        cititorMaria.setAbonament(new Abonament(TipAbonament.STUDENT, "2026-10-01", "2027-06-30"));

        cititorService.adaugaCititor(cititorAna);
        cititorService.adaugaCititor(cititorIon);
        cititorService.adaugaCititor(cititorMaria);

        System.out.println("Cititori inregistrati:");
        cititorAna.afiseaza();
        cititorIon.afiseaza();
        cititorMaria.afiseaza();

        printSection("3. IMPRUMUT CARTE - CAZ VALID");

        Imprumut imprumutAna = cititorService.imprumutaCarte(carteMarquez, cititorAna, "2026-05-18");

        System.out.println("Imprumut realizat cu succes:");
        System.out.println(imprumutAna);

        printSection("4. IMPRUMUT CARTE INDISPONIBILA - EXCEPTIE");

        try {
            cititorService.imprumutaCarte(carteMarquez, cititorIon, "2026-05-18");
        } catch (CarteIndisponibilaException e) {
            System.out.println("Exceptie prinsa: " + e.getMessage());
        }

        printSection("5. RETURNARE CARTE");

        cititorService.returneazaCarte(imprumutAna.getId(), "2026-05-25");

        System.out.println("Cartea a fost returnata.");
        System.out.println("Status imprumut: " + imprumutAna.getStatus().getDescriere());
        System.out.println("Carte disponibila acum: " + carteMarquez.getDisponibilitate());

        printSection("6. CAUTARE CARTI DUPA AUTOR");

        List<Carte> cartiGarcia = carteService.cautaDupaAutor("Garcia");

        System.out.println("Carti de Garcia Marquez:");
        for (Carte carte : cartiGarcia) {
            carte.afiseaza();
        }

        printSection("7. CAUTARE CARTE DUPA TITLU");

        Carte carteGasita = carteService.cautaDupaTitlu("Dune");

        System.out.println("Carte gasita:");
        carteGasita.afiseaza();

        printSection("8. LISTARE CARTI PE SECTIUNE");

        carteService.listeazaDupaSectiune("Fantezie & SF");

        printSection("9. LISTARE CARTI DISPONIBILE");

        carteService.listeazaDisponibile();

        printSection("10. ISTORICUL IMPRUMUTURILOR UNUI CITITOR");

        cititorService.listaIstoric(cititorAna.getId());

        printSection("11. STERGERE CITITOR CU ID INVALID - EXCEPTIE");

        try {
            cititorService.stergeCititor(999);
        } catch (CititorNegasitException e) {
            System.out.println("Exceptie prinsa: " + e.getMessage());
        }

        printSection("12. MANAGER CU ECHIPA");

        Angajat angajatElena = new Angajat(
                "Elena Dumitrescu",
                "elena.d@biblioteca.ro",
                RolAngajat.BIBLIOTECAR,
                4200.0
        );

        Angajat angajatRadu = new Angajat(
                "Radu Popa",
                "radu.p@biblioteca.ro",
                RolAngajat.ARHIVAR,
                3800.0
        );

        Manager managerCristina = new Manager(
                "Cristina Vlad",
                "cristina.vlad@biblioteca.ro",
                "Colectii & Resurse",
                7500.0
        );

        managerCristina.adaugaAngajat(angajatElena);
        managerCristina.adaugaAngajat(angajatRadu);

        System.out.println("Manager:");
        managerCristina.afiseaza();

        System.out.println("Echipa:");
        for (Angajat angajat : managerCristina.getEchipa()) {
            System.out.println("  " + angajat);
        }

        printSection("13. EVENIMENT LITERAR");

        Eveniment evenimentDune = new Eveniment(
                "Lansarea romanului 'Dune'",
                "2026-06-10",
                "Sala Mare, Biblioteca Centrala",
                TipEveniment.LANSARE,
                autorHerbert
        );

        evenimentDune.adaugaParticipant(cititorAna);
        evenimentDune.adaugaParticipant(cititorMaria);

        System.out.println("Eveniment creat:");
        System.out.println(evenimentDune);

        System.out.println("Participanti:");
        for (Cititor participant : evenimentDune.getParticipanti()) {
            System.out.println("  " + participant);
        }

        printSection("FINAL");

        System.out.println("Toate actiunile principale au fost executate.");
        System.out.println("Daca serviciile apeleaza AuditService, actiunile au fost inregistrate in audit.csv.");
    }

    private static void printSection(String title) {
        System.out.println();
        System.out.println(SEP);
        System.out.println(title);
        System.out.println(SEP);
    }
}