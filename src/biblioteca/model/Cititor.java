package biblioteca.model;

public class Cititor extends Persoana{
    protected int nrCartiImprumutate;
    private static int contor = 1;

    public Cititor(String nume, String email){
        super(contor++,nume,email);
        this.nrCartiImprumutate = 0;
    }

    public void setNrCartiImprumutate(int nr){
        nrCartiImprumutate +=nr;
    }

    public int getNrCartiImprumutate() {
        return nrCartiImprumutate;
    }

    @Override
    public String getRol() {
        return "Cititor";
    }

    @Override
    public String toString(){
        return String.format("Id = %d\nNume = %s\nEmail = %s\nNumarul de carti imprumutate: %d\n\n",id,nume_complet,email,nrCartiImprumutate);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Cititor)) return false;
        Cititor c = (Cititor) o;
        return c.id == this.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.id);
    }
}
