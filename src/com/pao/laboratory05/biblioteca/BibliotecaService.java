package com.pao.laboratory05.biblioteca;


import java.util.Arrays;
import java.util.Comparator;

//Constructor privat, getInstance() cu Holder intern (pattern din Lab 01)
//Câmp: private Carte[] carti (inițializat new Carte[0])
//void addCarte(Carte carte) — resize + adaugă + printează confirmare
//void listSortedByRating() — clonează, Arrays.sort(copy) (natural = Comparable), afișează
//void listSortedBy(Comparator<Carte> comparator) — clonează, Arrays.sort(copy, comparator), afișează
public class BibliotecaService {
    private Carte[] carti;
    private static BibliotecaService instance;

    private BibliotecaService() {
        carti = new Carte[0];
    }

    public static BibliotecaService getInstance(){
        if (instance == null)
            return new BibliotecaService();
        return instance;
    }

    void addCarte (Carte carte) {
        Carte[] new_carti = new Carte[carti.length+1];
        System.arraycopy(carti, 0, new_carti, 0, carti.length);
        new_carti[carti.length] = carte;
        carti = new_carti;
        System.out.println("Carte adaugata: "+ carte.getTitlu());
    }

    void listSortedByRating() {
        Carte[] copy = carti.clone();
        Arrays.sort(copy);
        for (var carte: copy) {
            System.out.println(carte);
        }
    }

    void listSortedBy(Comparator<Carte> comparator){
        Carte[] copy = carti.clone();
        Arrays.sort(copy,comparator);
        for (var carte: copy) {
            System.out.println(carte);
        }
    }


}
