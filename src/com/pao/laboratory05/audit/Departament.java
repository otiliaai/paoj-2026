package com.pao.laboratory05.audit;

public record Departament(String nume, String locatie) {
    //NU AM NEVOIE DE EL pentru ca este generat automat de record
//    @Override
//    public String toString() {
//        return "Departament[nume="+this.nume()+", locatie="+this.locatie()+"]";
//    }
}
