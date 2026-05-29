package com.pao.proiect.biblioteca.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class AuditService {
    private static AuditService instance;
    private static final String FISIER_AUDIT = "audit.csv";

    private AuditService() {
    }

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }

        return instance;
    }

    public synchronized void logheaza(String var1) {
        try (PrintWriter var2 = new PrintWriter(new FileWriter("audit.csv", true))) {
            var2.println(var1 + "," + String.valueOf(LocalDateTime.now()));
        } catch (IOException var7) {
            System.err.println("Eroare la scriere audit: " + var7.getMessage());
        }

    }
}
