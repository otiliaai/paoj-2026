package com.pao.proiect.biblioteca.repository;

import com.pao.proiect.biblioteca.model.Autor;
import com.pao.proiect.biblioteca.model.Carte;
import com.pao.proiect.biblioteca.model.GenLiterar;
import com.pao.proiect.biblioteca.model.ISBN;
import com.pao.proiect.biblioteca.model.Sectiune;
import com.pao.proiect.biblioteca.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarteRepository implements Repository<Carte, Integer> {
    private final Connection connection;

    public CarteRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Carte entity) {
        String sql = "insert into carti (id, autor_id, sectiune_id, titlu, isbn, disponibilitate) values (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.setInt(2, entity.getAutor().getId());
            statement.setInt(3, entity.getSectiune().getId());
            statement.setString(4, entity.getTitlu());
            statement.setString(5, entity.getIsbn().getCod());
            statement.setBoolean(6, entity.getDisponibilitate());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea cartii", e);
        }
    }

    @Override
    public Optional<Carte> findById(Integer id) {
        String sql = """
                select
                    c.id as carte_id,
                    c.titlu,
                    c.isbn,
                    c.disponibilitate,
                    
                    a.id as autor_id,
                    a.nume_complet as autor_nume,
                    a.nationalitate as autor_nationalitate,

                    s.id as sectiune_id,
                    s.nume_sectiune,
                    s.descriere,
                    s.gen_literar
                from carti c
                join autori a on c.autor_id = a.id
                join sectiuni s on c.sectiune_id = s.id
                where c.id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Carte carte = mapResultSetToCarte(resultSet);
                    return Optional.of(carte);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea cartii cu id-ul = " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Carte> findAll() {
        String sql = """
                select
                    c.id as carte_id,
                    c.titlu,
                    c.isbn,
                    c.disponibilitate,

                    a.id as autor_id,
                    a.nume_complet as autor_nume,
                    a.nationalitate as autor_nationalitate,

                    s.id as sectiune_id,
                    s.nume_sectiune,
                    s.descriere,
                    s.gen_literar
                from carti c
                join autori a on c.autor_id = a.id
                join sectiuni s on c.sectiune_id = s.id
                """;

        List<Carte> carti = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Carte carte = mapResultSetToCarte(resultSet);
                carti.add(carte);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea cartilor", e);
        }

        return carti;
    }

    @Override
    public void update(Carte entity) {
        String sql = """
                update carti
                set autor_id = ?, sectiune_id = ?, titlu = ?, isbn = ?, disponibilitate = ?
                where id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getAutor().getId());
            statement.setInt(2, entity.getSectiune().getId());
            statement.setString(3, entity.getTitlu());
            statement.setString(4, entity.getIsbn().getCod());
            statement.setBoolean(5, entity.getDisponibilitate());
            statement.setInt(6, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea cartii cu id-ul = " + entity.getId(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from carti where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea cartii cu id-ul = " + id, e);
        }
    }

    private Carte mapResultSetToCarte(ResultSet resultSet) throws SQLException {
        Autor autor = new Autor(
                resultSet.getString("autor_nume"),
                resultSet.getString("autor_nationalitate")
        );

        Sectiune sectiune = new Sectiune(
                resultSet.getInt("sectiune_id"),
                resultSet.getString("nume_sectiune"),
                resultSet.getString("descriere"),
                GenLiterar.valueOf(resultSet.getString("gen_literar"))
        );

        ISBN isbn = new ISBN(
                resultSet.getString("isbn"),
                "necunoscuta",
                0
        );

        Carte carte = new Carte(
                resultSet.getInt("carte_id"),
                resultSet.getString("titlu"),
                autor,
                sectiune,
                isbn
        );
        carte.setDisponibilitate(resultSet.getBoolean("disponibilitate"));

        return carte;
    }
}