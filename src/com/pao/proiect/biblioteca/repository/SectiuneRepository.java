package com.pao.proiect.biblioteca.repository;
import com.pao.proiect.biblioteca.model.GenLiterar;
import com.pao.proiect.biblioteca.model.Sectiune;
import com.pao.proiect.biblioteca.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SectiuneRepository implements Repository<Sectiune, Integer> {
    private final Connection connection;

    public SectiuneRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Sectiune entity) {
        String sql = "insert into sectiuni (id, nume_sectiune, descriere, gen_literar) values (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.setString(2, entity.getNume());
            statement.setString(3, entity.getDescriere());
            statement.setString(4, entity.getGen().name());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea sectiunii", e);
        }
    }

    @Override
    public Optional<Sectiune> findById(Integer id) {
        String sql = "select * from sectiuni where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Sectiune sectiune = new Sectiune(
                            resultSet.getInt("id"),
                            resultSet.getString("nume_sectiune"),
                            resultSet.getString("descriere"),
                            GenLiterar.valueOf(resultSet.getString("gen_literar"))
                    );

                    return Optional.of(sectiune);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea sectiunii cu id-ul = " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Sectiune> findAll() {
        String sql = "select * from sectiuni";
        List<Sectiune> sectiuni = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Sectiune sectiune = new Sectiune(
                        resultSet.getInt("id"),
                        resultSet.getString("nume_sectiune"),
                        resultSet.getString("descriere"),
                        GenLiterar.valueOf(resultSet.getString("gen_literar"))
                );

                sectiuni.add(sectiune);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea sectiunilor", e);
        }

        return sectiuni;
    }

    @Override
    public void update(Sectiune entity) {
        String sql = "update sectiuni set nume_sectiune = ?, descriere = ?, gen_literar = ? where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, entity.getNume());
            statement.setString(2, entity.getDescriere());
            statement.setString(3, entity.getGen().name());
            statement.setInt(4, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea sectiunii cu id-ul = " + entity.getId(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from sectiuni where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea sectiunii cu id-ul = " + id, e);
        }
    }
}