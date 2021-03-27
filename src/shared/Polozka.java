package shared;

import java.io.Serializable;

public class Polozka implements Serializable{
    int id;
    String nazev;
    int cena;

	public Polozka() {
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

    public void setCena(int cena) {
        this.cena = cena;
    }

}
