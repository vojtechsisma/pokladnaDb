package shared;

import java.io.Serializable;
import java.util.LinkedList;

public class Polozka implements Serializable{
    
    LinkedList<Polozka> pridavky = new LinkedList<Polozka>();
    
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
        int celkovaCena = 0;
		for (int i = 0; i < this.pridavky.size(); i++) {
			celkovaCena += pridavky.get(i).getCena();
		}
		return celkovaCena + this.cena;
    }


    public Polozka getPridavky(int index) {
        return pridavky.get(index);
    }

    public int getPridavkySize() {
        return pridavky.size();
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public void pridej(Polozka polozka) {
		pridavky.add(polozka);
	}

}
