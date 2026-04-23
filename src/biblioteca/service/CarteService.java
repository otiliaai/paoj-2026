package biblioteca.service;
import java.util.*;
import biblioteca.model.*;
public class CarteService {

    private static CarteService instance;
    private CarteService() {}
    private List<Carte> carti = new ArrayList<>();
    // pentru acces prin index, cu duplicate
    private TreeSet<Carte> cartiSortate = new TreeSet<>();
    // ordonat automat folosind compareTo din carte, fara duplicate(set)
    private Map<String, List<Carte>> cartiDupaAutor = new HashMap<>();


    public static CarteService getInstance() {
        if (instance == null) instance = new CarteService();
        return instance;
    }

    public void adaugaCarte(Carte carte) {
        carti.add(carte);
        cartiSortate.add(carte);

        String autor_carte = carte.getAutor().getNume();
        if (cartiDupaAutor.containsKey(autor_carte)) {
            cartiDupaAutor.get(autor_carte).add(carte);
        }
        else {
            List<Carte> lista = new ArrayList<>();
            lista.add(carte);
            cartiDupaAutor.put(autor_carte,lista);
        }
    }

    public void stergeCarte(int id) {
        carti.removeIf(c -> c.getId() == id);
        cartiSortate.removeIf(c -> c.getId() == id);
        for (var entry: cartiDupaAutor.values()) {
            entry.removeIf( c -> c.getId() == id);
        }
    }

    public Carte cautaDupaId(int id) {
        for (var carte: cartiSortate){
            if (carte.getId() == id)
                return carte;
        }
        return null;
    }

    public Carte cautaDupaTitlu(String titlu) {
        for (var carte: cartiSortate){
            if (carte.getTitlu().equals(titlu))
                return carte;
        }
        return null;
    }

    public List<Carte> cautaDupaAutor(String nume_autor) {
        if (cartiDupaAutor.containsKey(nume_autor))
            return cartiDupaAutor.get(nume_autor);
        return new ArrayList<>();
    }

    public void listeazToate(){
        for ( var c: cartiSortate)
            System.out.println(c);
    }

    public void listeazaDisponibile(){
        for ( var c: cartiSortate){
            if (c.getDisponibilitate() == true)
                System.out.println(c);
        }
    }

    public void listeazaDupaSectiune(String numeSectiune) {
        int nr = 0;
        for (var c : cartiSortate) {
            if (c.getSectiune().getNume().equals(numeSectiune)) {
                nr++;
                System.out.println(c);
            }
        }
        if (nr == 0)
            System.out.println(String.format("Nu exista carti disponibile in sectiune %s", numeSectiune));

    }
}

