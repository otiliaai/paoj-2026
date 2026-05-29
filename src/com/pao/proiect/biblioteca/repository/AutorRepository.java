package com.pao.proiect.biblioteca.repository;
import com.pao.proiect.biblioteca.model.Autor;
import com.pao.proiect.biblioteca.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AutorRepository implements Repository<Autor, Integer> {

    private final Connection connection;

    public AutorRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Autor entity) {
        String sql = "insert into autori (id, nume_complet, nationalitate) values (?, ?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.setString(2,entity.getNumeComplet());
            statement.setString(3, entity.getNationalitate());

            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException("Eroare la salvarea autorului", e);
        }
    }

    @Override
    public Optional<Autor> findById(Integer id) {
        String sql = "select id, nume_complet, nationalitate from autori a where a.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);

            try (ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    Autor autor = new Autor(
                            resultSet.getInt("id"),
                            resultSet.getString("nume_complet"),
                            resultSet.getString("nationalitate")
                    );
                    return Optional.of(autor);
                }
            }

        }
        catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea autorului cu id-ul = "+id,e);
        }

        return Optional.empty();
    }

    @Override
    public List<Autor> findAll() {
        String sql = "select * from autori";
        List<Autor> autori = new ArrayList<>() ;

        try(PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Autor autor = new Autor(
                        resultSet.getInt("id"),
                        resultSet.getString("nume_complet"),
                        resultSet.getString("nationalitate")
                );
                autori.add(autor);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea autorilor",e);
        }
        return autori;
    }

    @Override
    public void update(Autor entity) {
        String sql = "update autori set nume_complet = ?, nationalitate = ? where id = ?";

        try( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1,entity.getNumeComplet());
            statement.setString(2, entity.getNationalitate());
            statement.setInt(3, entity.getId());

            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea autorului cu id-ul = "+entity.getId(),e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from autori where id = ?";

        try( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea autorului cu id-ul = "+id,e);
        }
    }
}

