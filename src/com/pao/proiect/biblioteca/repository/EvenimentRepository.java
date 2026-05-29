package com.pao.proiect.biblioteca.repository;

import com.pao.proiect.biblioteca.model.Autor;
import com.pao.proiect.biblioteca.model.Eveniment;
import com.pao.proiect.biblioteca.model.TipEveniment;
import com.pao.proiect.biblioteca.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EvenimentRepository implements Repository<Eveniment, Integer> {
    private final Connection connection;

    public EvenimentRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Eveniment entity) {
        String sql = "insert into evenimente (id, autor_id, titlu, data_eveniment, locatie, tip_eveniment) values (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.setInt(2, entity.getAutor().getId());
            statement.setString(3, entity.getTitlu());
            statement.setDate(4, java.sql.Date.valueOf(entity.getData()));
            statement.setString(5, entity.getLocatie());
            statement.setString(6, entity.getTip().name());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea evenimentului", e);
        }
    }

    @Override
    public Optional<Eveniment> findById(Integer id) {
        String sql = "select " +
                "e.id as eveniment_id, " +
                "e.titlu, " +
                "e.data_eveniment, " +
                "e.locatie, " +
                "e.tip_eveniment, " +
                "a.id as autor_id, " +
                "a.nume_complet as autor_nume, " +
                "a.nationalitate as autor_nationalitate " +
                "from evenimente e " +
                "join autori a on e.autor_id = a.id " +
                "where e.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Autor autor = new Autor(
                            resultSet.getString("autor_nume"),
                            resultSet.getString("autor_nationalitate")
                    );

                    Eveniment eveniment = new Eveniment(
                            resultSet.getInt("eveniment_id"),
                            resultSet.getString("titlu"),
                            resultSet.getString("data_eveniment"),
                            resultSet.getString("locatie"),
                            TipEveniment.valueOf(resultSet.getString("tip_eveniment")),
                            autor
                    );

                    return Optional.of(eveniment);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea evenimentului cu id-ul = " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Eveniment> findAll() {
        String sql = "select " +
                "e.id as eveniment_id, " +
                "e.titlu, " +
                "e.data_eveniment, " +
                "e.locatie, " +
                "e.tip_eveniment, " +
                "a.id as autor_id, " +
                "a.nume_complet as autor_nume, " +
                "a.nationalitate as autor_nationalitate " +
                "from evenimente e " +
                "join autori a on e.autor_id = a.id";

        List<Eveniment> evenimente = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Autor autor = new Autor(
                        resultSet.getString("autor_nume"),
                        resultSet.getString("autor_nationalitate")
                );

                Eveniment eveniment = new Eveniment(
                        resultSet.getInt("eveniment_id"),
                        resultSet.getString("titlu"),
                        resultSet.getString("data_eveniment"),
                        resultSet.getString("locatie"),
                        TipEveniment.valueOf(resultSet.getString("tip_eveniment")),
                        autor
                );

                evenimente.add(eveniment);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea evenimentelor", e);
        }

        return evenimente;
    }

    @Override
    public void update(Eveniment entity) {
        String sql = "update evenimente " +
                "set autor_id = ?, titlu = ?, data_eveniment = ?, locatie = ?, tip_eveniment = ? " +
                "where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getAutor().getId());
            statement.setString(2, entity.getTitlu());
            statement.setDate(3, java.sql.Date.valueOf(entity.getData()));
            statement.setString(4, entity.getLocatie());
            statement.setString(5, entity.getTip().name());
            statement.setInt(6, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea evenimentului cu id-ul = " + entity.getId(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from evenimente where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea evenimentului cu id-ul = " + id, e);
        }
    }
}