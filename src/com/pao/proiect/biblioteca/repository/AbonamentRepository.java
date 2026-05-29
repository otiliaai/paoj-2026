package com.pao.proiect.biblioteca.repository;

import com.pao.proiect.biblioteca.model.Abonament;
import com.pao.proiect.biblioteca.model.TipAbonament;
import com.pao.proiect.biblioteca.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AbonamentRepository implements Repository<Abonament, Integer> {
    private final Connection connection;

    public AbonamentRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Abonament entity) {
        String sql = "insert into abonamente (id, tip_abonament, data_start, data_expirare, activ) values (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.setString(2, entity.getTip().name());
            statement.setDate(3, java.sql.Date.valueOf(entity.getDataStart()));
            statement.setDate(4, java.sql.Date.valueOf(entity.getDataExpirare()));
            statement.setBoolean(5, entity.esteActiv());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea abonamentului", e);
        }
    }

    @Override
    public Optional<Abonament> findById(Integer id) {
        String sql = "select * from abonamente where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Abonament abonament = new Abonament(
                            resultSet.getInt("id"),
                            TipAbonament.valueOf(resultSet.getString("tip_abonament")),
                            resultSet.getString("data_start"),
                            resultSet.getString("data_expirare"),
                            resultSet.getBoolean("activ")
                    );

                    return Optional.of(abonament);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea abonamentului cu id-ul = " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Abonament> findAll() {
        String sql = "select * from abonamente";
        List<Abonament> abonamente = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Abonament abonament = new Abonament(
                        resultSet.getInt("id"),
                        TipAbonament.valueOf(resultSet.getString("tip_abonament")),
                        resultSet.getString("data_start"),
                        resultSet.getString("data_expirare"),
                        resultSet.getBoolean("activ")
                );

                abonamente.add(abonament);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea abonamentelor", e);
        }

        return abonamente;
    }

    @Override
    public void update(Abonament entity) {
        String sql = "update abonamente set tip_abonament = ?, data_start = ?, data_expirare = ?, activ = ? where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getTip().name());
            statement.setDate(2, java.sql.Date.valueOf(entity.getDataStart()));
            statement.setDate(3, java.sql.Date.valueOf(entity.getDataExpirare()));
            statement.setBoolean(4, entity.esteActiv());
            statement.setInt(5, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea abonamentului cu id-ul = " + entity.getId(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from abonamente where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea abonamentului cu id-ul = " + id, e);
        }
    }
}