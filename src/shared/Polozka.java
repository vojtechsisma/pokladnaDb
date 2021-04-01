package shared;

import java.io.Serializable;
import java.util.LinkedList;

public class Polozka implements Serializable {

    LinkedList<Pridavek> pridavky = new LinkedList<Pridavek>();

    int id;
    String nazev;
    int cena;
    int mnozstvi;
    int celkovaCena = 0;

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
        for (int i = 0; i < this.pridavky.size(); i++) {
            celkovaCena += pridavky.get(i).getCena();
        }
        return celkovaCena + this.cena;
    }

    public int getZakladniCena() {
        return this.cena;
    }


    public int getMnozstvi() {
        return this.mnozstvi;
    }

    public Pridavek getPridavky(int index) {
        return pridavky.get(index);
    }

    public LinkedList<Pridavek> getPridavkyList() {
        return pridavky;
    }

    public int getPridavkySize() {
        return pridavky.size();
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public void pridej(Pridavek pridavek) {
        pridavky.add(pridavek);
    }

    public void obnov() {
        this.pridavky = new LinkedList<Pridavek>();

        this.id = 0;
        this.nazev = null;
        this.cena = 0;
        this.mnozstvi = 0;
        this.celkovaCena = 0;

    }

}
