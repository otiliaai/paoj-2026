package com.pao.proiect.biblioteca.model;

import java.util.Objects;

public final class ISBN {
    private final String cod;
    private final String tara;
    private final int an;

    public ISBN(String var1, String var2, int var3) {
        this.cod = var1;
        this.tara = var2;
        this.an = var3;
    }

    public ISBN(String isbn, String cod, String tara, int an) {
        this.cod = cod;
        this.tara = tara;
        this.an = an;
    }

    public String getCod() {
        return this.cod;
    }

    public String getTara() {
        return this.tara;
    }

    public int getAn() {
        return this.an;
    }

    public String toString() {
        return "ISBN{cod='" + this.cod + "', tara='" + this.tara + "', an=" + this.an + "}";
    }

    public boolean equals(Object var1) {
        if (this == var1) {
            return true;
        } else if (!(var1 instanceof ISBN)) {
            return false;
        } else {
            ISBN var2 = (ISBN)var1;
            return Objects.equals(this.cod, var2.cod);
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.cod});
    }
}
