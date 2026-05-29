package com.pao.proiect.biblioteca;

import com.pao.proiect.biblioteca.model.*;
import com.pao.proiect.biblioteca.repository.*;
import com.pao.proiect.biblioteca.service.AuditService;
import com.pao.proiect.biblioteca.service.BibliotecaJoinService;
import com.pao.proiect.biblioteca.service.BibliotecaTransactionService;
import com.pao.proiect.biblioteca.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        BibliotecaJoinService joinService = BibliotecaJoinService.getInstance();
        AuditService auditService = AuditService.getInstance();

        String suffix = String.valueOf(System.currentTimeMillis() % 1000000); //pt numere random ca sa fac campuri unice
        System.out.println("\n\n\n\n");
        System.out.println("1. REPOSITORY CRUD ---------------------------------------------------------------");


        // autori
        Autor autor1 = new Autor("Frank Herbert", "americana");
        autorRepository.save(autor1);
        auditService.logheaza("adauga_autor");

        Autor autor2 = new Autor("Isaac Asimov", "americana");
        autorRepository.save(autor2);
        auditService.logheaza("adauga_autor");

        Autor autor3 = new Autor("Jules Verne", "franceza");
        autorRepository.save(autor3);
        auditService.logheaza("adauga_autor");

        //sectiuni
        Sectiune sectiune1 = new Sectiune("Science Fiction " + suffix, "Carti SF si aventuri spatiale", GenLiterar.BIOGRAFIE);
        sectiuneRepository.save(sectiune1);
        auditService.logheaza("adauga_sectiune");

        Sectiune sectiune2 = new Sectiune("Aventura " + suffix, "Carti de aventura", GenLiterar.BIOGRAFIE);
        sectiuneRepository.save(sectiune2);
        auditService.logheaza("adauga_sectiune");

        //abonamente
        Abonament abonament1 = new Abonament(TipAbonament.ANUAL, "2026-01-01", "2026-12-31");
        abonamentRepository.save(abonament1);
        auditService.logheaza("adauga_abonament");

        Abonament abonament2 = new Abonament(TipAbonament.ANUAL, "2026-02-01", "2027-02-01");
        abonamentRepository.save(abonament2);
        auditService.logheaza("adauga_abonament");

        //cititori
        Cititor cititor1 = new Cititor("Ana Popescu", "ana.popescu" + suffix + "@email.com");
        cititor1.setAbonament(abonament1);
        cititorRepository.save(cititor1);
        auditService.logheaza("adauga_cititor");

        Cititor cititor2 = new Cititor("Mihai Ionescu", "mihai.ionescu" + suffix + "@email.com");
        cititor2.setAbonament(abonament2);
        cititorRepository.save(cititor2);
        auditService.logheaza("adauga_cititor");

        //carti
        ISBN isbn1 = new ISBN("9780000" + suffix, "SUA", 1965);
        Carte carte1 = new Carte("Dune", autor1, sectiune1, isbn1);
        carteRepository.save(carte1);
        auditService.logheaza("adauga_carte");

        ISBN isbn2 = new ISBN("9781000" + suffix, "SUA", 1969);
        Carte carte2 = new Carte("Dune Messiah", autor1, sectiune1, isbn2);
        carteRepository.save(carte2);
        auditService.logheaza("adauga_carte");

        ISBN isbn3 = new ISBN("9782000" + suffix, "SUA", 1951);
        Carte carte3 = new Carte("Foundation", autor2, sectiune1, isbn3);
        carteRepository.save(carte3);
        auditService.logheaza("adauga_carte");

        ISBN isbn4 = new ISBN("9783000" + suffix, "Franta", 1870);
        Carte carte4 = new Carte("20.000 de leghe sub mari", autor3, sectiune2, isbn4);
        carteRepository.save(carte4);
        auditService.logheaza("adauga_carte");

        // evenimente
        Eveniment eveniment1 = new Eveniment("Seara SF " + suffix, "2026-06-10", "Biblioteca Centrala", TipEveniment.LANSARE, autor1);
        evenimentRepository.save(eveniment1);
        auditService.logheaza("adauga_eveniment");

        Eveniment eveniment2 = new Eveniment("Club de lectura " + suffix, "2026-07-15", "Sala 2", TipEveniment.LANSARE, autor2);
        evenimentRepository.save(eveniment2);
        auditService.logheaza("adauga_eveniment");

        //participanti la evenimente
        adaugaParticipantLaEveniment(eveniment1.getId(), cititor1.getId());
        auditService.logheaza("adauga_participant_eveniment");

        adaugaParticipantLaEveniment(eveniment1.getId(), cititor2.getId());
        auditService.logheaza("adauga_participant_eveniment");

        adaugaParticipantLaEveniment(eveniment2.getId(), cititor2.getId());
        auditService.logheaza("adauga_participant_eveniment");

        // imprumut
        Imprumut imprumutSimplu = new Imprumut(cititor1, carte1, "2026-06-01");
        imprumutRepository.save(imprumutSimplu);
        auditService.logheaza("adauga_imprumut_repository");

        System.out.println("\n\n\n");
        System.out.println("\nAUTORI: ");
        autorRepository.findAll().forEach(System.out::println);
        auditService.logheaza("listeaza_autori");

        System.out.println("\nSECTIUNI: ");
        sectiuneRepository.findAll().forEach(System.out::println);
        auditService.logheaza("listeaza_sectiuni");

        System.out.println("\nABONAMENTE: ");
        abonamentRepository.findAll().forEach(System.out::println);
        auditService.logheaza("listeaza_abonamente");

        System.out.println("\nCITITORI: ");
        cititorRepository.findAll().forEach(System.out::println);
        auditService.logheaza("listeaza_cititori");

        System.out.println("\nCARTI: ");
        carteRepository.findAll().forEach(System.out::println);
        auditService.logheaza("listeaza_carti");

        System.out.println("\nEVENIMENTE: ");
        evenimentRepository.findAll().forEach(System.out::println);
        auditService.logheaza("listeaza_evenimente");

        System.out.println("\nIMPRUMUTURI: ");
        imprumutRepository.findAll().forEach(System.out::println);
        auditService.logheaza("listeaza_imprumuturi");

        System.out.println("\n\n\n");
        System.out.println("\nFIND BY ID: ");
        System.out.println("Autor id " + autor1.getId() + ": " + autorRepository.findById(autor1.getId()));
        auditService.logheaza("cauta_autor_dupa_id");

        System.out.println("Carte id " + carte1.getId() + ": " + carteRepository.findById(carte1.getId()));
        auditService.logheaza("cauta_carte_dupa_id");

        System.out.println("Cititor id " + cititor1.getId() + ": " + cititorRepository.findById(cititor1.getId()));
        auditService.logheaza("cauta_cititor_dupa_id");

        // update uri
        autor1.setNationalitate("americana actualizata");
        autorRepository.update(autor1);
        auditService.logheaza("actualizeaza_autor");

        carte1.setTitlu("Dune - editia actualizata");
        carteRepository.update(carte1);
        auditService.logheaza("actualizeaza_carte");

        cititor1.setEmail("ana.popescu.actualizat" + suffix + "@email.com");
        cititorRepository.update(cititor1);
        auditService.logheaza("actualizeaza_cititor");

        System.out.println("\nDUPA UPDATE: ");
        autorRepository.findAll().forEach(System.out::println);
        carteRepository.findAll().forEach(System.out::println);
        cititorRepository.findAll().forEach(System.out::println);
        auditService.logheaza("listeaza_dupa_update");

        System.out.println("\n\n\n\n\n");
        System.out.println("2. TRANZACTII JDBC: ");

        System.out.println("\n\nIMPRUMUTARE CARTE: ");
        transactionService.imprumutaCarte(cititor1.getId(), carte2.getId());
        auditService.logheaza("imprumuta_carte");

        System.out.println("\n\nDATE DUPA IMPRUMUTARE :");
        imprumutRepository.findAll().forEach(System.out::println);
        carteRepository.findAll().forEach(System.out::println);
        cititorRepository.findAll().forEach(System.out::println);
        auditService.logheaza("verifica_dupa_imprumutare");

        int imprumutTranzactieId = getUltimulImprumutId(imprumutRepository);

        System.out.println("\n\nRETURNARE CARTE :");
        transactionService.returneazaCarte(imprumutTranzactieId);
        auditService.logheaza("returneaza_carte");

        System.out.println("\n\nDATE DUPA RETURNARE :");
        imprumutRepository.findAll().forEach(System.out::println);
        carteRepository.findAll().forEach(System.out::println);
        cititorRepository.findAll().forEach(System.out::println);
        auditService.logheaza("verifica_dupa_returnare");

        System.out.println("\n\nIMPRUMUTARE ALTA CARTE: ");
        transactionService.imprumutaCarte(cititor2.getId(), carte3.getId());
        auditService.logheaza("imprumuta_carte");

        System.out.println("\n\nDATE DUPA A DOUA IMPRUMUTARE: ");
        imprumutRepository.findAll().forEach(System.out::println);
        carteRepository.findAll().forEach(System.out::println);
        cititorRepository.findAll().forEach(System.out::println);
        auditService.logheaza("verifica_dupa_a_doua_imprumutare");

        System.out.println("\n\n\n\n\n");
        System.out.println("3. JOIN");

        System.out.println("\n1)CARTI & AUTOR & SECTIUNE :");
        joinService.listeazaCartiCuAutorSiSectiune();
        auditService.logheaza("join_carti_autori_sectiuni");

        System.out.println("\n2)2: IMPRUMUTURI & CITITOR & CARTE& AUTOR :");
        joinService.listeazaImprumuturiCuCititorCarteAutor();
        auditService.logheaza("join_imprumuturi_cititori_carti_autori");

        System.out.println("\n3: TOP CARTI IMPRUMUTATE & AUTOR ---");
        joinService.listeazaTopCartiImprumutateCuAutor();
        auditService.logheaza("join_top_carti_imprumutate");

        System.out.println("\n4) CITITORI & ABONAMENT : ");
        joinService.listeazaCititoriCuAbonament();
        auditService.logheaza("join_cititori_abonamente");

        System.out.println("\n5)EVENIMENTE CU AUTOR SI PARTICIPANTI:");
        joinService.listeazaEvenimenteCuAutorSiParticipanti();
        auditService.logheaza("join_evenimente_autori_participanti");

        System.out.println("\n\n\n\n\n");
        System.out.println("4. AUDIT SERVICE:");


        System.out.println("\n--- ULTIMELE ACTIUNI DIN AUDIT.CSV ---");
        auditService.getEntries().forEach(System.out::println);

    }

    private static int getUltimulImprumutId(ImprumutRepository imprumutRepository) {
        return imprumutRepository.findAll()
                .stream()
                .max(Comparator.comparingInt(Imprumut::getId))
                .map(Imprumut::getId)
                .orElseThrow(() -> new RuntimeException("Nu exista niciun imprumut in baza de date."));
    }

    private static void adaugaParticipantLaEveniment(int evenimentId, int cititorId) {
        //folosesc ignore in caz daca apare eroare de duplicat sa nu se opreasca
        String sql = """
                insert ignore into participanti_evenimente(id_eveniment, id_cititor)
                values (?, ?)
                """;

        Connection connection = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, evenimentId);
            ps.setInt(2, cititorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la adaugarea participantului la eveniment", e);
        }
    }
}