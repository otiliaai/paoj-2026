package biblioteca.model;

public class Autor {
    protected int id;
    protected String nume;
    protected String nationalitate;

    public Autor(int id, String nume, String nationalitate) {
        this.id = id;
        this.nume = nume;
        this.nationalitate = nationalitate;
    }

    public int getId(){
        return this.id;
    }

    public String getNume(){
        return this.nume;
    }

    public String getNationalitate(){
        return this.nationalitate;
    }

    public void setNume(String nume_nou) {
        this.nume = nume_nou;
    }

    public void setNationalitate(String n) {
        this.nationalitate = n;
    }

    @Override
    public String toString() {
        return String.format("ID: %d\nNume: %s\nNationalitate: %s\n\n",id,nume,nationalitate);
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(id);
    }

    @Override
    public boolean equals(Object o){
        if (o == this) return true;
        if (!(o instanceof Autor)) return false;

        Autor autor = (Autor) o;
        return autor.id == this.id;

    }

}
