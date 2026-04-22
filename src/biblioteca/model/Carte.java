package biblioteca.model;

public class Carte implements Comparable<Carte>{
    protected int id;
    protected String titlu;
    protected Autor autor;
    protected Sectiune sectiune;
    protected ISBN isbn;
    protected boolean disponibilitate;


    public Carte(int id, String titlu, Autor autor, Sectiune sectiune, ISBN isbn, boolean disponibilitate) {
        this.id = id;
        this.titlu = titlu;
        this.autor = autor;
        this.sectiune = sectiune;
        this.isbn = isbn;
        this.disponibilitate = true;
    }

    public int getId(){
        return this.id;
    }

    public String getTitlu(){
        return this.titlu;
    }

    public Autor getAutor(){
        return this.autor;
    }

    public Sectiune getSectiune(){
        return this.sectiune;
    }

    public ISBN getIsbn(){
        return this.isbn;
    }

    public boolean getDisponibilitate(){
        return this.disponibilitate;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public void setSectiune(Sectiune s){
        this.sectiune = s;
    }

    public void setDisponibilitate(boolean val) {
        this.disponibilitate = val;
    }

    @Override
    public String toString(){
        return String.format("ID carte: %d\nTitlu carte: %s" +
                "\nAutor carte: %s\nSectiunea cartii: %s\nISBN: %s\nDisponibilitate: %s",
                id,titlu,autor,sectiune,isbn,disponibilitate);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof  Carte)) return false;

        Carte c = (Carte) o;
        return c.id == this.id;
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(id);
    }

    @Override
    public int compareTo(Carte other) {
        return this.titlu.compareTo(other.titlu);
    }
}
