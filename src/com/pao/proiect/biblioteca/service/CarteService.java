package com.pao.proiect.biblioteca.service;

import com.pao.proiect.biblioteca.exception.CarteNegasitaException;
import com.pao.proiect.biblioteca.model.Carte;
import com.pao.proiect.biblioteca.util.Searchable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class CarteService implements Searchable<Carte> {
    private static CarteService instance;
    private final Map<Integer, Carte> cartiById = new HashMap();
    private final TreeSet<Carte> cartiSortate = new TreeSet();

    private CarteService() {
    }

    public static CarteService getInstance() {
        if (instance == null) {
            instance = new CarteService();
        }

        return instance;
    }

    public void adaugaCarte(Carte var1) {
        this.cartiById.put(var1.getId(), var1);
        this.cartiSortate.add(var1);
        AuditService.getInstance().logheaza("adauga_carte");
    }

    public void stergeCarte(int var1) {
        Carte var2 = (Carte)this.cartiById.remove(var1);
        if (var2 == null) {
            throw new CarteNegasitaException(var1);
        } else {
            this.cartiSortate.remove(var2);
            AuditService.getInstance().logheaza("sterge_carte");
        }
    }

    public Carte cautaDupaId(int var1) {
        AuditService.getInstance().logheaza("cauta_carte_id");
        Carte var2 = (Carte)this.cartiById.get(var1);
        if (var2 == null) {
            throw new CarteNegasitaException(var1);
        } else {
            return var2;
        }
    }

    public Carte cautaDupaTitlu(String var1) {
        AuditService.getInstance().logheaza("cauta_carte_titlu");
        return (Carte)this.cartiById.values().stream().filter((var1x) -> var1x.getTitlu().equalsIgnoreCase(var1)).findFirst().orElseThrow(() -> new CarteNegasitaException(-1));
    }

    public List<Carte> cautaDupaAutor(String var1) {
        AuditService.getInstance().logheaza("cauta_carte_autor");
        ArrayList var2 = new ArrayList();

        for(Carte var4 : this.cartiById.values()) {
            if (var4.getAutor().getNumeComplet().toLowerCase().contains(var1.toLowerCase())) {
                var2.add(var4);
            }
        }

        return var2;
    }

    public void listeazaDupaSectiune(String var1) {
        AuditService.getInstance().logheaza("listeaza_sectiune");
        System.out.println("Carti din sectiunea '" + var1 + "':");
        this.cartiSortate.stream().filter((var1x) -> var1x.getSectiune().getNume().equalsIgnoreCase(var1)).forEach(Carte::afiseaza);
    }

    public void listeazaDisponibile() {
        AuditService.getInstance().logheaza("listeaza_disponibile");
        System.out.println("Carti disponibile:");
        this.cartiSortate.stream().filter(Carte::getDisponibilitate).forEach(Carte::afiseaza);
    }

    public List<Carte> cautaDupa(String var1) {
        ArrayList var2 = new ArrayList();
        String var3 = var1.toLowerCase();

        for(Carte var5 : this.cartiById.values()) {
            if (var5.getTitlu().toLowerCase().contains(var3) || var5.getAutor().getNumeComplet().toLowerCase().contains(var3)) {
                var2.add(var5);
            }
        }

        return var2;
    }

    public Map<Integer, Carte> getCartiById() {
        return this.cartiById;
    }

    public TreeSet<Carte> getCartiSortate() {
        return this.cartiSortate;
    }
}
