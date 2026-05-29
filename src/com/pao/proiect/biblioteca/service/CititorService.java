package com.pao.proiect.biblioteca.service;

import com.pao.proiect.biblioteca.exception.CarteIndisponibilaException;
import com.pao.proiect.biblioteca.exception.CititorNegasitException;
import com.pao.proiect.biblioteca.exception.ImprumutNegasitException;
import com.pao.proiect.biblioteca.exception.LimitaImprumutDepasitaException;
import com.pao.proiect.biblioteca.model.Carte;
import com.pao.proiect.biblioteca.model.Cititor;
import com.pao.proiect.biblioteca.model.Imprumut;
import com.pao.proiect.biblioteca.model.StatusImprumut;
import com.pao.proiect.biblioteca.util.Searchable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CititorService implements Searchable<Cititor> {
    private static CititorService instance;
    private final Map<Integer, Cititor> cititori = new HashMap();
    private final List<Imprumut> imprumuturi = new ArrayList();
    private static final int LIMITA_IMPRUMUTURI = 3;

    private CititorService() {
    }

    public static CititorService getInstance() {
        if (instance == null) {
            instance = new CititorService();
        }

        return instance;
    }

    public void adaugaCititor(Cititor var1) {
        this.cititori.put(var1.getId(), var1);
        AuditService.getInstance().logheaza("adauga_cititor");
    }

    public void stergeCititor(int var1) {
        Cititor var2 = (Cititor)this.cititori.remove(var1);
        if (var2 == null) {
            throw new CititorNegasitException(var1);
        } else {
            AuditService.getInstance().logheaza("sterge_cititor");
        }
    }

    public Cititor cautaDupaId(int var1) {
        Cititor var2 = (Cititor)this.cititori.get(var1);
        if (var2 == null) {
            throw new CititorNegasitException(var1);
        } else {
            return var2;
        }
    }

    public Imprumut imprumutaCarte(Carte var1, Cititor var2, String var3) {
        if (!this.cititori.containsKey(var2.getId())) {
            throw new CititorNegasitException(var2.getId());
        } else if (!var1.getDisponibilitate()) {
            throw new CarteIndisponibilaException(var1.getTitlu());
        } else if (var2.getNrCartiImprumutate() >= 3) {
            throw new LimitaImprumutDepasitaException(3);
        } else {
            Imprumut var4 = new Imprumut(var2, var1, var3);
            var1.setDisponibilitate(false);
            var2.adaugaImprumut(var4);
            this.imprumuturi.add(var4);
            AuditService.getInstance().logheaza("imprumuta_carte");
            return var4;
        }
    }

    public void returneazaCarte(int var1, String var2) {
        Imprumut var3 = (Imprumut)this.imprumuturi.stream().filter((var1x) -> var1x.getId() == var1).findFirst().orElseThrow(() -> new ImprumutNegasitException(var1));
        var3.setStatus(StatusImprumut.RETURNAT);
        var3.setDataReturnare(var2);
        var3.getCarte().setDisponibilitate(true);
        var3.getCititor().returneazaCarte();
        AuditService.getInstance().logheaza("returneaza_carte");
    }

    public void listaIstoric(int var1) {
        Cititor var2 = this.cautaDupaId(var1);
        AuditService.getInstance().logheaza("istoric_cititor");
        System.out.println("Istoricul imprumuturilor pentru " + var2.getNumeComplet() + ":");
        if (var2.getIstoric().isEmpty()) {
            System.out.println("  (niciun imprumut)");
        } else {
            var2.getIstoric().forEach((var0) -> System.out.println("  " + String.valueOf(var0)));
        }
    }

    public List<Cititor> cautaDupa(String var1) {
        ArrayList var2 = new ArrayList();
        String var3 = var1.toLowerCase();

        for(Cititor var5 : this.cititori.values()) {
            if (var5.getNumeComplet().toLowerCase().contains(var3) || var5.getEmail().toLowerCase().contains(var3)) {
                var2.add(var5);
            }
        }

        return var2;
    }

    public Map<Integer, Cititor> getCititori() {
        return this.cititori;
    }

    public List<Imprumut> getImprumuturi() {
        return this.imprumuturi;
    }
}
