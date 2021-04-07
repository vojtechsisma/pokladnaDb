package shared;

import java.io.Serializable;

public class Pridavek implements Serializable {

    private static final long serialVersionUID = 1L;

    int id;
    String nazev;
    int cena;
    int mnozstvi;
    int celkovaCena = 0;
    int parent;

    public Pridavek() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazev() {
        return this.nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public int getCena() {

        return this.cena;
    }

    public int getMnozstvi() {
        return this.mnozstvi;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public int getParent() {
        return this.parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

}
