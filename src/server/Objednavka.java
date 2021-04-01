package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import shared.Polozka;

public class Objednavka extends UnicastRemoteObject implements shared.Objednavka {

    LinkedList<Polozka> polozky = new LinkedList<Polozka>();

    int id;
    LocalDateTime casObjednavky;
    int cena;
    int celkovaCena = 0;

    public Objednavka() throws RemoteException {
        super();
        this.casObjednavky = LocalDateTime.now();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public LocalDateTime getCasObjednavky() {
        return this.casObjednavky;
    }

    @Override
    public int getCena() {
        return this.cena;
    }

    @Override
    public void setCena(int cena) {
        this.cena = cena;
    }

    @Override
    public void pridej(Polozka polozka) {
        polozky.add(polozka);
    }

    @Override
    public int getCelkovaCena() {
        
        for (int i = 0; i < this.polozky.size(); i++) {
            celkovaCena += polozky.get(i).getCena();
        }
        return celkovaCena;
    }

    @Override
    public void vypis() {
        for (Iterator i = polozky.iterator(); i.hasNext();) {
            System.out.println(i.next());
        }
    }

    @Override
    public LinkedList<Polozka> getPolozky() {
        return polozky;
    }

    @Override
    public void obnov() {
        this.casObjednavky = LocalDateTime.now();
        this.polozky = new LinkedList<Polozka>();

        this.id = 0;
        this.cena = 0;
        this.celkovaCena = 0;

    }

}
