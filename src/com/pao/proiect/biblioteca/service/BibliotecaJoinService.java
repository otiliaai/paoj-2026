package com.pao.proiect.biblioteca.service;
import com.pao.proiect.biblioteca.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BibliotecaJoinService {
    private static BibliotecaJoinService instance;

    private BibliotecaJoinService() {
    }

    public static BibliotecaJoinService getInstance() {
        if (instance == null) {
            instance = new BibliotecaJoinService();
        }
        return instance;
    }

    public void listeazaCartiCuAutorSiSectiune() {
        String sql = """
                select c.id as carte_id,
                       c.titlu as titlu_carte,
                       c.isbn as isbn,
                       c.disponibilitate as disponibilitate,
                       a.nume_complet as autor,
                       s.nume_sectiune as sectiune,
                       s.gen_literar as gen_literar
                from carti c
                join autori a on c.autor_id = a.id
                join sectiuni s on c.sectiune_id = s.id
                order by c.titlu
                """;

        Connection connection = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) {
                System.out.println(
                        "CarteJoin{" +
                                "id=" + rs.getInt("carte_id") +
                                ", titlu='" + rs.getString("titlu_carte") + '\'' +
                                ", autor='" + rs.getString("autor") + '\'' +
                                ", sectiune='" + rs.getString("sectiune") + '\'' +
                                ", gen='" + rs.getString("gen_literar") + '\'' +
                                ", isbn='" + rs.getString("isbn") + '\'' +
                                ", disponibila=" + rs.getBoolean("disponibilitate") + '}'
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la JOIN carti-autori-sectiuni.", e);
        }
    }

    public void listeazaImprumuturiCuCititorCarteAutor() {
        String sql = """
                select i.id as imprumut_id,
                       ci.nume_complet as cititor,
                       ci.email as email,
                       c.titlu as carte,
                       a.nume_complet as autor,
                       i.data_imprumut as data_imprumut,
                       i.data_returnare as data_returnare,
                       i.status as status
                from imprumuturi i
                join cititori ci on i.cititor_id = ci.id
                join carti c on i.carte_id = c.id
                join autori a on c.autor_id = a.id
                order by i.id
                """;

        Connection connection = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) {
                System.out.println(
                        "ImprumutJoin{" +
                                "id=" + rs.getInt("imprumut_id") +
                                ", cititor='" + rs.getString("cititor") + '\'' +
                                ", email='" + rs.getString("email") + '\'' +
                                ", carte='" + rs.getString("carte") + '\'' +
                                ", autor='" + rs.getString("autor") + '\'' +
                                ", dataImprumut='" + rs.getDate("data_imprumut") + '\'' +
                                ", dataReturnare='" + rs.getDate("data_returnare") + '\'' +
                                ", status='" + rs.getString("status") + '\'' +
                                '}');
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la JOIN imprumuturi-cititori-carti-autori.", e);
        }
    }

    public void listeazaTopCartiImprumutateCuAutor() {
        String sql = """
                select c.id as carte_id,
                       c.titlu as titlu_carte,
                       a.nume_complet as autor,
                       count(i.id) as numar_imprumuturi
                from carti c
                join autori a on c.autor_id = a.id
                left join imprumuturi i on c.id = i.carte_id
                group by c.id, c.titlu, a.nume_complet
                order by numar_imprumuturi desc, c.titlu
                limit 5""";

        Connection connection = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next()) {
                System.out.println(
                        "TopCarte{" + "id=" + rs.getInt("carte_id") + ", titlu='" + rs.getString("titlu_carte") + '\'' +
                                ", autor='" + rs.getString("autor") + '\'' +
                                ", numarImprumuturi=" + rs.getInt("numar_imprumuturi") + '}');
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la JOIN top carti imprumutate", e);
        }
    }

    public void listeazaCititoriCuAbonament() {
        String sql = """
                select ci.id as cititor_id,
                       ci.nume_complet as cititor,
                       ci.email as email,
                       ci.numar_carti as numar_carti,
                       a.tip_abonament as tip_abonament,
                       a.data_start as data_start,
                       a.data_expirare as data_expirare,
                       a.activ as activ
                from cititori ci
                left join abonamente a on ci.abonament_id = a.id
                order by ci.nume_complet
                """;

        Connection connection = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.println(
                        "CititorAbonament{" +
                                "id=" + rs.getInt("cititor_id") +
                                ", nume='" + rs.getString("cititor") + '\'' +
                                ", email='" + rs.getString("email") + '\'' +
                                ", numarCarti=" + rs.getInt("numar_carti") +
                                ", tipAbonament='" + rs.getString("tip_abonament") + '\'' +
                                ", dataStart='" + rs.getDate("data_start") + '\'' +
                                ", dataExpirare='" + rs.getDate("data_expirare") + '\'' +
                                ", activ=" + rs.getBoolean("activ") + '}');
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la JOIN cititori-abonamente", e);
        }
    }

    public void listeazaEvenimenteCuAutorSiParticipanti() {
        String sql = """
                select e.id as eveniment_id,
                       e.titlu as titlu_eveniment,
                       e.data_eveniment as data_eveniment,
                       e.locatie as locatie,
                       e.tip_eveniment as tip_eveniment,
                       a.nume_complet as autor,
                       count(pe.id_cititor) as numar_participanti
                from evenimente e
                join autori a on e.autor_id = a.id
                left join participanti_evenimente pe on e.id = pe.id_eveniment
                group by e.id, e.titlu, e.data_eveniment, e.locatie, e.tip_eveniment, a.nume_complet
                order by e.data_eveniment""";

        Connection connection = DatabaseConnection.getInstance().getConnection();

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                System.out.println(
                        "EvenimentJoin{" +
                                "id=" + rs.getInt("eveniment_id") +
                                ", titlu='" + rs.getString("titlu_eveniment") + '\'' +
                                ", data='" + rs.getDate("data_eveniment") + '\'' +
                                ", locatie='" + rs.getString("locatie") + '\'' +
                                ", tip='" + rs.getString("tip_eveniment") + '\'' +
                                ", autor='" + rs.getString("autor") + '\'' +
                                ", participanti=" + rs.getInt("numar_participanti") +'}');
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la JOIN evenimente-autori-participanti.", e);
        }
    }
}