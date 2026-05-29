package com.pao.proiect.biblioteca;

import com.pao.proiect.biblioteca.model.*;
import com.pao.proiect.biblioteca.repository.*;
import com.pao.proiect.biblioteca.service.BibliotecaTransactionService;

import java.util.Comparator;

public class TestRepositoryMain {
    public static void main(String[] args) {
        AutorRepository autorRepository = new AutorRepository();
        SectiuneRepository sectiuneRepository = new SectiuneRepository();
        AbonamentRepository abonamentRepository = new AbonamentRepository();
        CititorRepository cititorRepository = new CititorRepository();
        CarteRepository carteRepository = new CarteRepository();
        EvenimentRepository evenimentRepository = new EvenimentRepository();
        ImprumutRepository imprumutRepository = new ImprumutRepository();

        BibliotecaTransactionService transactionService = BibliotecaTransactionService.getInstance();

        //creez autor
        Autor autor = new Autor("Frank Herbert", "americana");
        autorRepository.save(autor);

        //creez sectiune
        Sectiune sectiune = new Sectiune("Science Fiction", "Carti SF", GenLiterar.BIOGRAFIE);
        sectiuneRepository.save(sectiune);

        //creez abonament
        Abonament abonament = new Abonament(
                TipAbonament.ANUAL,
                "2026-01-01",
                "2026-12-31"
        );
        abonamentRepository.save(abonament);

        // creez cititor cu abonament
        Cititor cititor = new Cititor("Ana Popescu", "ana.popescu@email.com");
        cititor.setAbonament(abonament);
        cititorRepository.save(cititor);

        //creez carte pentru testarea CRUD-ului normal
        ISBN isbn = new ISBN("9780441013593", "SUA", 1965);
        Carte carte = new Carte("Dune", autor, sectiune, isbn);
        carteRepository.save(carte);

        // creez eveniment
        Eveniment eveniment = new Eveniment(
                "Seara SF",
                "2026-06-10",
                "Biblioteca Centrala",
                TipEveniment.LANSARE,
                autor
        );
        evenimentRepository.save(eveniment);

        // creez imprumut simplu, prin repository
        // Acesta testeaza CRUD-ul pentru ImprumutRepository.
        Imprumut imprumut = new Imprumut(cititor, carte, "2026-06-01");
        imprumutRepository.save(imprumut);

        // creez o carte separata pentru testarea tranzactiilor JDBC

        ISBN isbnTranzactie = new ISBN("9780441013594", "SUA", 1969);
        Carte cartePentruTranzactie = new Carte("Dune Messiah", autor, sectiune, isbnTranzactie);
        carteRepository.save(cartePentruTranzactie);

        // testare jdbc: imprumutare carte
        System.out.println("\n--- TRANZACTIE JDBC: IMPRUMUTARE CARTE ---");
        transactionService.imprumutaCarte(cititor.getId(), cartePentruTranzactie.getId());

        System.out.println("\n--- IMPRUMUTURI DUPA TRANZACTIA DE IMPRUMUTARE ---");
        imprumutRepository.findAll().forEach(System.out::println);

        System.out.println("\n--- CARTI DUPA TRANZACTIA DE IMPRUMUTARE ---");
        carteRepository.findAll().forEach(System.out::println);

        System.out.println("\n--- CITITORI DUPA TRANZACTIA DE IMPRUMUTARE ---");
        cititorRepository.findAll().forEach(System.out::println);

        int imprumutTranzactieId = imprumutRepository.findAll()
                .stream()
                .max(Comparator.comparingInt(Imprumut::getId))
                .map(Imprumut::getId)
                .orElseThrow(() -> new RuntimeException("Nu exista niciun imprumut in baza de date."));

        System.out.println("\n--- TRANZACTIE JDBC: RETURNARE CARTE ---");
        transactionService.returneazaCarte(imprumutTranzactieId);

        System.out.println("\n--- IMPRUMUTURI DUPA TRANZACTIA DE RETURNARE ---");
        imprumutRepository.findAll().forEach(System.out::println);

        System.out.println("\n--- CARTI DUPA TRANZACTIA DE RETURNARE ---");
        carteRepository.findAll().forEach(System.out::println);

        System.out.println("\n--- CITITORI DUPA TRANZACTIA DE RETURNARE ---");
        cititorRepository.findAll().forEach(System.out::println);

        // findall
        System.out.println("\n--- AUTORI ---");
        autorRepository.findAll().forEach(System.out::println);

        System.out.println("\n--- SECTIUNI ---");
        sectiuneRepository.findAll().forEach(System.out::println);

        System.out.println("\n--- ABONAMENTE ---");
        abonamentRepository.findAll().forEach(System.out::println);

        System.out.println("\n--- CITITORI ---");
        cititorRepository.findAll().forEach(System.out::println);

        System.out.println("\n--- CARTI ---");
        carteRepository.findAll().forEach(System.out::println);

        System.out.println("\n--- EVENIMENTE ---");
        evenimentRepository.findAll().forEach(System.out::println);

        System.out.println("\n--- IMPRUMUTURI ---");
        imprumutRepository.findAll().forEach(System.out::println);

        // findbyid
        System.out.println("\n--- FIND BY ID ---");
        System.out.println("Autor id " + autor.getId() + ": " + autorRepository.findById(autor.getId()));
        System.out.println("Carte id " + carte.getId() + ": " + carteRepository.findById(carte.getId()));
        System.out.println("Cititor id " + cititor.getId() + ": " + cititorRepository.findById(cititor.getId()));

        //update
        autor.setNationalitate("americana actualizata");
        autorRepository.update(autor);

        carte.setTitlu("Dune - editia actualizata");
        carteRepository.update(carte);

        cititor.setNrCartiImprumutate(1);
        cititorRepository.update(cititor);

        System.out.println("\n--- DUPA UPDATE ---");
        autorRepository.findAll().forEach(System.out::println);
        carteRepository.findAll().forEach(System.out::println);
        cititorRepository.findAll().forEach(System.out::println);

        //testare delete
        evenimentRepository.delete(eveniment.getId());

        System.out.println("\n--- EVENIMENTE DUPA DELETE ---");
        evenimentRepository.findAll().forEach(System.out::println);

        System.out.println("\nTest finalizat cu succes.");
    }
}