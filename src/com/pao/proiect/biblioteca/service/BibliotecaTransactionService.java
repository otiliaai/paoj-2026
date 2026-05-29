package com.pao.proiect.biblioteca.service;

import com.pao.proiect.biblioteca.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BibliotecaTransactionService {
    private static BibliotecaTransactionService instance;
    private final AuditService auditService = AuditService.getInstance();

    private BibliotecaTransactionService() {
    }

    public static BibliotecaTransactionService getInstance() {
        if (instance == null) {
            instance = new BibliotecaTransactionService();
        }

        return instance;
    }

    public void imprumutaCarte(int cititorId, int carteId) {
        String verificaCititorSql = """
                select id
                from cititori
                where id = ?
                """;

        String verificaCarteSql = """
                select id, disponibilitate
                from carti
                where id = ?
                """;

        String insertImprumutSql = """
                insert into imprumuturi(cititor_id, carte_id, data_imprumut, data_returnare, status)
                values (?, ?, ?, null, ?)
                """;

        String updateCarteSql = """
                update carti
                set disponibilitate = false
                where id = ?
                """;

        String updateCititorSql = """
                update cititori
                set numar_carti = numar_carti + 1
                where id = ?
                """;

        Connection connection = DatabaseConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            try {
                try (PreparedStatement ps = connection.prepareStatement(verificaCititorSql)) {
                    ps.setInt(1, cititorId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Cititorul nu exista.");
                        }
                    }
                }

                try (PreparedStatement ps = connection.prepareStatement(verificaCarteSql)) {
                    ps.setInt(1, carteId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Cartea nu exista.");
                        }

                        boolean disponibilitate = rs.getBoolean("disponibilitate");

                        if (!disponibilitate) {
                            throw new SQLException("Cartea nu este disponibila.");
                        }
                    }
                }

                try (PreparedStatement ps = connection.prepareStatement(insertImprumutSql)) {
                    ps.setInt(1, cititorId);
                    ps.setInt(2, carteId);
                    ps.setDate(3, Date.valueOf(LocalDate.now()));
                    ps.setString(4, "ACTIV");

                    ps.executeUpdate();
                }

                try (PreparedStatement ps = connection.prepareStatement(updateCarteSql)) {
                    ps.setInt(1, carteId);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = connection.prepareStatement(updateCititorSql)) {
                    ps.setInt(1, cititorId);
                    ps.executeUpdate();
                }
                auditService.logheaza("imprumuta_carte");
                connection.commit();

                System.out.println("Imprumutul a fost realizat cu succes.");

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Tranzactia de imprumut a fost anulata: " + e.getMessage(), e);

            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la tranzactia de imprumut.", e);
        }
    }

    public void returneazaCarte(int imprumutId) {
        String cautaImprumutSql = """
                select id, cititor_id, carte_id, status
                from imprumuturi
                where id = ?
                """;

        String updateImprumutSql = """
                update imprumuturi
                set data_returnare = ?, status = ?
                where id = ?
                """;

        String updateCarteSql = """
                update carti
                set disponibilitate = true
                where id = ?
                """;

        String updateCititorSql = """
                update cititori
                set numar_carti = greatest(numar_carti - 1, 0)
                where id = ?
                """;

        Connection connection = DatabaseConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            try {
                int cititorId;
                int carteId;

                try (PreparedStatement ps = connection.prepareStatement(cautaImprumutSql)) {
                    ps.setInt(1, imprumutId);

                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Imprumutul nu exista.");
                        }

                        String status = rs.getString("status");

                        if (!"ACTIV".equalsIgnoreCase(status)) {
                            throw new SQLException("Imprumutul este deja returnat sau nu este activ.");
                        }

                        cititorId = rs.getInt("cititor_id");
                        carteId = rs.getInt("carte_id");
                    }
                }

                try (PreparedStatement ps = connection.prepareStatement(updateImprumutSql)) {
                    ps.setDate(1, Date.valueOf(LocalDate.now()));
                    ps.setString(2, "RETURNAT");
                    ps.setInt(3, imprumutId);

                    ps.executeUpdate();
                }

                try (PreparedStatement ps = connection.prepareStatement(updateCarteSql)) {
                    ps.setInt(1, carteId);
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = connection.prepareStatement(updateCititorSql)) {
                    ps.setInt(1, cititorId);
                    ps.executeUpdate();
                }
                auditService.logheaza("returneaza_carte");
                connection.commit();

                System.out.println("Cartea a fost returnata cu succes.");

            } catch (SQLException e) {
                connection.rollback();
                throw new RuntimeException("Tranzactia de returnare a fost anulata: " + e.getMessage(), e);

            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la tranzactia de returnare.", e);
        }
    }
}