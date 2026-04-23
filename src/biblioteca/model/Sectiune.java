package biblioteca.model;

public class Sectiune {
    protected int id;
    protected String nume;
    protected String descriere;
    private static int contor = 1;

    public Sectiune(String nume, String descriere) {
        this.id = id;
        this.descriere = descriere;
        this.nume = nume;
        this.id = contor++;
    }

    public int getId(){
        return this.id;
    }

    public String getNume(){
        return this.nume;
    }
    public String getDescriere(){
        return this.descriere;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    @Override
    public String toString(){
        return String.format("ID sectiune: %d\nNume: %s\nDescriere: %s\n\n",id,nume,descriere);
    }
}

