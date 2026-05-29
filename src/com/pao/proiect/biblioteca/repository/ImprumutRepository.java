package com.pao.proiect.biblioteca.repository;

import com.pao.proiect.biblioteca.model.Autor;
import com.pao.proiect.biblioteca.model.Carte;
import com.pao.proiect.biblioteca.model.Cititor;
import com.pao.proiect.biblioteca.model.GenLiterar;
import com.pao.proiect.biblioteca.model.ISBN;
import com.pao.proiect.biblioteca.model.Imprumut;
import com.pao.proiect.biblioteca.model.Sectiune;
import com.pao.proiect.biblioteca.model.StatusImprumut;
import com.pao.proiect.biblioteca.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImprumutRepository implements Repository<Imprumut, Integer> {
    private final Connection connection;

    public ImprumutRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Imprumut entity) {
        String sql = "insert into imprumuturi (id, cititor_id, carte_id, data_imprumut, data_returnare, status) " +
                "values (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getId());
            statement.setInt(2, entity.getCititor().getId());
            statement.setInt(3, entity.getCarte().getId());
            statement.setDate(4, java.sql.Date.valueOf(entity.getDataImprumut()));

            if (entity.getDataReturnare() != null) {
                statement.setDate(5, java.sql.Date.valueOf(entity.getDataReturnare()));
            } else {
                statement.setNull(5, Types.DATE);
            }

            statement.setString(6, entity.getStatus().name());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la salvarea imprumutului", e);
        }
    }

    @Override
    public Optional<Imprumut> findById(Integer id) {
        String sql = "select " +
                "i.id as imprumut_id, " +
                "i.data_imprumut, " +
                "i.data_returnare, " +
                "i.status, " +

                "ct.id as cititor_id, " +
                "ct.nume_complet as cititor_nume, " +
                "ct.email as cititor_email, " +
                "ct.numar_carti, " +

                "c.id as carte_id, " +
                "c.titlu as carte_titlu, " +
                "c.isbn, " +
                "c.disponibilitate, " +

                "a.id as autor_id, " +
                "a.nume_complet as autor_nume, " +
                "a.nationalitate as autor_nationalitate, " +

                "s.id as sectiune_id, " +
                "s.nume_sectiune, " +
                "s.descriere, " +
                "s.gen_literar " +

                "from imprumuturi i " +
                "join cititori ct on i.cititor_id = ct.id " +
                "join carti c on i.carte_id = c.id " +
                "join autori a on c.autor_id = a.id " +
                "join sectiuni s on c.sectiune_id = s.id " +
                "where i.id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Imprumut imprumut = mapResultSetToImprumut(resultSet);
                    return Optional.of(imprumut);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea imprumutului cu id-ul = " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Imprumut> findAll() {
        String sql = "select " +
                "i.id as imprumut_id, " +
                "i.data_imprumut, " +
                "i.data_returnare, " +
                "i.status, " +

                "ct.id as cititor_id, " +
                "ct.nume_complet as cititor_nume, " +
                "ct.email as cititor_email, " +
                "ct.numar_carti, " +

                "c.id as carte_id, " +
                "c.titlu as carte_titlu, " +
                "c.isbn, " +
                "c.disponibilitate, " +

                "a.id as autor_id, " +
                "a.nume_complet as autor_nume, " +
                "a.nationalitate as autor_nationalitate, " +

                "s.id as sectiune_id, " +
                "s.nume_sectiune, " +
                "s.descriere, " +
                "s.gen_literar " +

                "from imprumuturi i " +
                "join cititori ct on i.cititor_id = ct.id " +
                "join carti c on i.carte_id = c.id " +
                "join autori a on c.autor_id = a.id " +
                "join sectiuni s on c.sectiune_id = s.id";

        List<Imprumut> imprumuturi = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Imprumut imprumut = mapResultSetToImprumut(resultSet);
                imprumuturi.add(imprumut);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea imprumuturilor", e);
        }

        return imprumuturi;
    }

    @Override
    public void update(Imprumut entity) {
        String sql = "update imprumuturi " +
                "set cititor_id = ?, carte_id = ?, data_imprumut = ?, data_returnare = ?, status = ? " +
                "where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, entity.getCititor().getId());
            statement.setInt(2, entity.getCarte().getId());
            statement.setDate(3, java.sql.Date.valueOf(entity.getDataImprumut()));

            if (entity.getDataReturnare() != null) {
                statement.setDate(4, java.sql.Date.valueOf(entity.getDataReturnare()));
            } else {
                statement.setNull(4, Types.DATE);
            }

            statement.setString(5, entity.getStatus().name());
            statement.setInt(6, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea imprumutului cu id-ul = " + entity.getId(), e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from imprumuturi where id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea imprumutului cu id-ul = " + id, e);
        }
    }

    private Imprumut mapResultSetToImprumut(ResultSet resultSet) throws SQLException {
        Cititor cititor = new Cititor(
                resultSet.getInt("cititor_id"),
                resultSet.getString("cititor_nume"),
                resultSet.getString("cititor_email"),
                resultSet.getInt("numar_carti")
        );

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
                resultSet.getString("carte_titlu"),
                autor,
                sectiune,
                isbn
        );

        carte.setDisponibilitate(resultSet.getBoolean("disponibilitate"));

        String dataReturnare = null;
        if (resultSet.getDate("data_returnare") != null) {
            dataReturnare = resultSet.getString("data_returnare");
        }

        return new Imprumut(
                resultSet.getInt("imprumut_id"),
                cititor,
                carte,
                resultSet.getString("data_imprumut"),
                dataReturnare,
                StatusImprumut.valueOf(resultSet.getString("status"))
        );
    }
}