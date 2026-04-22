package biblioteca.model;

public class ISBN {

    private final String cod;
    private final String tara;
    private final int an;

    public ISBN(String cod, String tara, int an){
        this.cod = cod;
        this.tara = tara;
        this.an = an;
    }

    public String getCod(){
        return this.cod;
    }

    public String getTara(){
        return this.tara;
    }

    public int getAn(){
        return this.an;
    }

    @Override
    public String toString(){
        return String.format("Cod: %s\n Tara: %s\nAn: %d\n\n",cod,tara,an);
    }

    @Override
    public int hashCode(){
        return cod.hashCode();
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof ISBN)) return false;
        ISBN new_o = (ISBN) o;
        return this.cod.equals(new_o.cod);
    }
}
