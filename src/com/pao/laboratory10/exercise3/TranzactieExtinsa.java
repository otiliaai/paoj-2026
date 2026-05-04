package com.pao.laboratory10.exercise3;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;

class TranzactieExtinsa extends Tranzactie {
    private String contSursa;

    TranzactieExtinsa(int id, double suma, String data, TipTranzactie tip, String contSursa) {
        super(id, suma, data, tip);
        this.contSursa = contSursa;
    }

    public String getContSursa() { return contSursa; }
}
