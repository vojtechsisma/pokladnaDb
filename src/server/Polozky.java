package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import server.db.Db;
import shared.Polozka;

public class Polozky extends UnicastRemoteObject implements shared.Polozky {

    protected Polozky() throws RemoteException {
        super();
        //TODO Auto-generated constructor stub
    }

    @Override
    public List<Polozka> getPolozky() throws RemoteException{
        List<Polozka> polozky = new ArrayList<>();

        try (Connection conn = Db.get().getConnection();
                Statement polozkyStmt = conn.createStatement();
                ResultSet polozkyRs = polozkyStmt.executeQuery("SELECT id, nazev, cena FROM polozky");
                PreparedStatement skupinyStmt = conn
                        .prepareStatement("SELECT id, oznaceni FROM skupiny WHERE trida = ?");) {

            while (polozkyRs.next()) {
                System.out.print(polozkyRs.getInt("id") + "\t");
                
                System.out.print(polozkyRs.getString("nazev") + "\t");
                
                System.out.println(polozkyRs.getInt("cena"));

                Polozka polozka = new Polozka();
                polozka.setId(polozkyRs.getInt("id"));
                polozka.setNazev(polozkyRs.getString("nazev"));
                polozka.setCena(polozkyRs.getInt("cena"));

                polozky.add(polozka);
            }

        } catch (Exception e) {
            return null;
        }

        return polozky;

    }

    @Override
    public List<Polozka> getPridavky() throws RemoteException{
        List<Polozka> polozky = new ArrayList<>();

        try (Connection conn = Db.get().getConnection();
                Statement pridavkyStmt = conn.createStatement();
                ResultSet polozkyRs = pridavkyStmt.executeQuery("SELECT id, nazev, cena, nadrazeny_id FROM pridavky");
                PreparedStatement skupinyStmt = conn
                        .prepareStatement("SELECT id, oznaceni FROM skupiny WHERE trida = ?");) {

            while (polozkyRs.next()) {
                Polozka polozka = new Polozka();
                polozka.setId(polozkyRs.getInt("id"));
                polozka.setNazev(polozkyRs.getString("nazev"));
                polozka.setCena(polozkyRs.getInt("cena"));

                polozky.add(polozka);
            }

        } catch (Exception e) {
            return null;
        }

        return polozky;

    }
}
