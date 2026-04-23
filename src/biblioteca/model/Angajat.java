package biblioteca.model;

public class Angajat extends Persoana {
    private String functie;
    private static int contor = 1;

    public Angajat(String nume, String email, String functie) {
        super(contor++,nume, email);
        this.functie = functie;
    }

    public void setFunctie(String functie){
        this.functie = functie;
    }

    public String getFunctie() {
        return functie;
    }

    @Override
    public String getRol() {
        return "Angajat";
    }

    @Override
    public String toString(){
        return String.format("Id = %d\nNume = %s\nEmail = %s\nFunctie: %s\n\n",id,nume_complet,email,functie);
    }
}
