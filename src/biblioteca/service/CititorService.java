package biblioteca.service;
import java.util.*;

import biblioteca.exception.CarteIndisponibilaException;
import biblioteca.exception.CititorNegasitException;
import biblioteca.model.*;

public class CititorService {
    private static CititorService instance;
    private CititorService() {}

    private Set<Cititor> cititori = new HashSet<>();
    private List<Imprumut> imprumuturi = new ArrayList<>();

    public static CititorService getInstance(){
        if (instance == null) instance =  new CititorService();
        return instance;
    }

    public void adaugaCititor(Cititor c){
        cititori.add(c);
    }

    public void stergeCititor(int id) throws CititorNegasitException {
        int lungime =cititori.toArray().length;
        cititori.removeIf( c-> c.getId() == id);
        if (cititori.toArray().length == lungime) {
            throw new CititorNegasitException(String.format("Cititorul cu id = %d nu exista",id));
        }
    }

    public Cititor cautaDupaId(int id) {
        for ( var c : cititori) {
            if (c.getId() == id)
                return c ;
        }
        return null;
    }

    public void ImprumutaCarte(Carte carte, Cititor cititor, String data_imprumut) throws CarteIndisponibilaException {
        if (!carte.getDisponibilitate())
            throw new CarteIndisponibilaException("Cartea "+ carte.getTitlu()+ " nu este disponibila");

        Imprumut imprumut = new Imprumut(imprumuturi.size()+1, cititor, carte, data_imprumut);
        imprumuturi.add(imprumut);
        carte.setDisponibilitate(false);
        cititor.setNrCartiImprumutate(1); //functia imi aduna nr dat ca parametru
    }

    public void returneazaCarte(int idImprumut, String data_retur) {
        for (var imprumut: imprumuturi) {
            if (imprumut.getId() == idImprumut) {
                imprumut.setDataReturnare(data_retur);
                imprumut.setStatusReturnare(true);
                imprumut.getCarte().setDisponibilitate(true);
                imprumut.getCititor().setNrCartiImprumutate(-1);
                break;
            }
        }
    }

    public void listeaza_istoric(int idCititor) {
        for (Imprumut i : imprumuturi) {
            if (i.getCititor().getId() == idCititor)
                System.out.println(i);
        }
    }
}
