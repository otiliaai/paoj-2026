package biblioteca.model;

public abstract class Persoana {
    protected int id;
    protected String nume_complet;
    protected String email;


    protected Persoana(int id, String nume, String email) {
        this.id = id;
        this.nume_complet = nume;
        this.email = email;
    }

    protected int getId() {
        return this.id;
    }

    public String getNume() {
        return this.nume_complet;
    }

    public String getEmail() {
        return this.email;
    }

    public abstract String getRol();

    @Override
    public String toString() {
        return String.format("Id = %d\n Nume = %s\nEmail = %s\n\n", id, nume_complet, email);
    }
}