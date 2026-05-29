package com.pao.proiect.biblioteca;

import com.pao.proiect.biblioteca.model.*;
import com.pao.proiect.biblioteca.repository.*;

public class TestRepositoryMain {
    public static void main(String[] args) {
        AutorRepository autorRepository = new AutorRepository();
        SectiuneRepository sectiuneRepository = new SectiuneRepository();
        AbonamentRepository abonamentRepository = new AbonamentRepository();
        CititorRepository cititorRepository = new CititorRepository();
        CarteRepository carteRepository = new CarteRepository();
        EvenimentRepository evenimentRepository = new EvenimentRepository();
        ImprumutRepository imprumutRepository = new ImprumutRepository();

        // 1. cream autor
        Autor autor = new Autor("Frank Herbert", "americana");
        autorRepository.save(autor);

        // 2. cream sectiune
        // schimba GenLiterar.SF daca enum-ul tau are alta valoare
        Sectiune sectiune = new Sectiune("Science Fiction", "Carti SF", GenLiterar.BIOGRAFIE);
        sectiuneRepository.save(sectiune);

        // 3. cream abonament
        // schimba TipAbonament.PREMIUM daca enum-ul tau are alta valoare
        Abonament abonament = new Abonament(
                TipAbonament.ANUAL,
                "2026-01-01",
                "2026-12-31"
        );
        abonamentRepository.save(abonament);

        // 4. cream cititor cu abonament
        Cititor cititor = new Cititor("Ana Popescu", "ana.popescu@email.com");
        cititor.setAbonament(abonament);
        cititorRepository.save(cititor);

        // 5. cream carte
        ISBN isbn = new ISBN("9780441013593", "SUA", 1965);
        Carte carte = new Carte("Dune", autor, sectiune, isbn);
        carteRepository.save(carte);

        // 6. cream eveniment
        // schimba TipEveniment.LANSARE daca enum-ul tau are alta valoare
        Eveniment eveniment = new Eveniment(
                "Seara SF",
                "2026-06-10",
                "Biblioteca Centrala",
                TipEveniment.LANSARE,
                autor
        );
        evenimentRepository.save(eveniment);

        // 7. cream imprumut
        Imprumut imprumut = new Imprumut(cititor, carte, "2026-06-01");
        imprumutRepository.save(imprumut);

        // 8. testam findAll
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

        // 9. testam findById
        System.out.println("\n--- FIND BY ID ---");
        System.out.println("Autor id " + autor.getId() + ": " + autorRepository.findById(autor.getId()));
        System.out.println("Carte id " + carte.getId() + ": " + carteRepository.findById(carte.getId()));
        System.out.println("Cititor id " + cititor.getId() + ": " + cititorRepository.findById(cititor.getId()));

        // 10. testam update
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

        // 11. testam delete pe o entitate simpla
        // Nu sterge autorul/sectiunea acum, pentru ca sunt legate de carte.
        // Poti testa delete pe eveniment:
        evenimentRepository.delete(eveniment.getId());

        System.out.println("\n--- EVENIMENTE DUPA DELETE ---");
        evenimentRepository.findAll().forEach(System.out::println);

        System.out.println("\nTest finalizat cu succes.");
    }
}