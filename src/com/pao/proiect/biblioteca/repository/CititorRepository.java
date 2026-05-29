package com.pao.proiect.biblioteca.repository;

import com.pao.proiect.biblioteca.model.Cititor;
import com.pao.proiect.biblioteca.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CititorRepository implements Repository<Cititor, Integer> {
    private final Connection connection;

    public CititorRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Cititor entity) {
        String sql = "insert into cititori (id, nume_complet, email, numar_carti, abonament_id) values (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.setString(2, entity.getNumeComplet());
            statement.setString(3, entity.getEmail());
            statement.setInt(4, entity.getNrCartiImprumutate());

            if (entity.getAbonament() != null) {
                statement.setInt(5, entity.getAbonament().getId());
            } else {
                statement.setNull(5, Types.INTEGER);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea cititorului", e);
        }
    }

    @Override
    public Optional<Cititor> findById(Integer id) {
        String sql = "select * from cititori where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Cititor cititor = new Cititor(
                            resultSet.getInt("id"),
                            resultSet.getString("nume_complet"),
                            resultSet.getString("email"),
                            resultSet.getInt("numar_carti")
                    );

                    return Optional.of(cititor);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea cititorului cu id-ul = " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Cititor> findAll() {
        String sql = "select * from cititori";
        List<Cititor> cititori = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Cititor cititor = new Cititor(
                        resultSet.getInt("id"),
                        resultSet.getString("nume_complet"),
                        resultSet.getString("email"),
                        resultSet.getInt("numar_carti")
                );

                cititori.add(cititor);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea cititorilor", e);
        }

        return cititori;
    }

    @Override
    public void update(Cititor entity) {
        String sql = "update cititori set nume_complet = ?, email = ?, numar_carti = ?, abonament_id = ? where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getNumeComplet());
            statement.setString(2, entity.getEmail());
            statement.setInt(3, entity.getNrCartiImprumutate());

            if (entity.getAbonament() != null) {
                statement.setInt(4, entity.getAbonament().getId());
            } else {
                statement.setNull(4, Types.INTEGER);
            }

            statement.setInt(5, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea cititorului cu id-ul = " + entity.getId(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from cititori where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea cititorului cu id-ul = " + id, e);
        }
    }
}