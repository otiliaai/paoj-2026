package com.pao.proiect.biblioteca.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AuditService {
    private static volatile AuditService instance;
    private static final String FISIER_AUDIT = "audit.csv";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private AuditService() {
        scrieHeaderDacaEsteNecesar();
    }

    public static AuditService getInstance() {
        if (instance == null) {
            synchronized (AuditService.class) {
                if (instance == null) {
                    instance = new AuditService();
                }
            }
        }

        return instance;
    }
    // ca sa fie thread-safe
    public synchronized void logheaza(String numeActiune) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FISIER_AUDIT, true))) //ca sa nu se suprascrie la rulare
        {
            writer.println(numeActiune + "," + LocalDateTime.now().format(FORMATTER));
        } catch (IOException e) {
            System.err.println("Eroare la scriere audit: " + e.getMessage());
        }
    }

    public synchronized List<String> getEntries() {
        List<String> intrari = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FISIER_AUDIT))) {
            String linie;

            while ((linie = reader.readLine()) != null) {
                if (!linie.isBlank()) {
                    intrari.add(linie);
                }
            }

        } catch (IOException e) {
            System.err.println("Eroare la citire audit: " + e.getMessage());
        }

        return intrari;
    }

    private void scrieHeaderDacaEsteNecesar() {
        File fisier = new File(FISIER_AUDIT);

        if (!fisier.exists() || fisier.length() == 0) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(FISIER_AUDIT, true))) {
                writer.println("nume_actiune,timestamp");
            } catch (IOException e) {
                System.err.println("Eroare la creare header audit: " + e.getMessage());
            }
        }
    }
}