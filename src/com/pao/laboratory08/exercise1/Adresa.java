package com.pao.laboratory08.exercise1;

public class Adresa implements Cloneable {
    private String oras;
    private String strada;

     Adresa(String oras, String strada){
         this.oras = oras;
         this.strada = strada;
     }

     public String getOras(){
         return this.oras;
    }

    public String getStrada(){
         return this.strada;
    }

    public void setOras(String oras){
        this.oras = oras;
    }
    public void setStrada(String strada){
        this.strada = strada;
    }

    @Override
    public String toString() {
         return String.format("Adresa{oras='%s', strada='%s'}",this.oras,this.strada);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}