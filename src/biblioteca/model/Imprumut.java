package biblioteca.model;

public class Imprumut {
    protected int id;
    protected Cititor cititor;
    protected Carte carte;
    protected String dataImprumut;
    protected String dataReturnare;
    protected boolean returnat ;


    public Imprumut(int id, Cititor cititor,  Carte carte, String dataImprumut) {
        this.id = id;
        this.cititor = cititor;
        this.carte = carte;
        this.dataImprumut = dataImprumut;
        this.dataReturnare = null;
        this.returnat = false;
    }

    public int getId(){
        return this.id;
    }

    public Cititor getCititor(){
        return this.cititor;
    }

    public Carte getCarte(){
        return this.carte;
    }

    public String getDataImprumut() {
        return this.dataImprumut;
    }

    public String getDataReturnare() {
        return this.dataReturnare;
    }

    public boolean getStatusRetur() {
        return this.returnat;
    }

    public void setDataReturnare(String dataRet) {
        this.dataReturnare = dataRet;
    }

    public void setStatusReturnare(boolean status){
        this.returnat = status;
    }

    @Override
    public String toString(){
        return String.format("Id carte imprumutata: %d\n" +
                "Cititor: %s\nCarte: %s\nData imprumutului: %s\n" +
                "Data retur: %s\nReturnat: %s",id,cititor,carte,dataImprumut,dataReturnare, returnat);
    }
}
