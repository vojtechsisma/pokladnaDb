package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import server.db.Db;
import server.Objednavka;
import shared.Polozka;
import shared.Pridavek;

public class Polozky extends UnicastRemoteObject implements shared.Polozky {

    protected Polozky() throws RemoteException {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<Polozka> getPolozky() throws RemoteException {
        List<Polozka> polozky = new ArrayList<>();

        try (Connection conn = Db.get().getConnection();
                Statement polozkyStmt = conn.createStatement();
                ResultSet polozkyRs = polozkyStmt.executeQuery("SELECT id, nazev, cena FROM polozky");) {

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

    @Override
    public List<Pridavek> getPridavky(int id) throws RemoteException {
        List<Pridavek> polozky = new ArrayList<>();

        try (Connection conn = Db.get().getConnection();
                PreparedStatement pridavkyStmt = conn.prepareStatement(
                        "SELECT pridavky.id, pridavky.nazev, pridavky.cena FROM pridavky JOIN pridavkyvpolozce ON pridavky.id=pridavkyvpolozce.pridavky_id JOIN polozky ON polozky.id=pridavkyvpolozce.polozky_id WHERE polozky.id = ?");) {

            pridavkyStmt.setInt(1, id);
            try (ResultSet polozkyRs = pridavkyStmt.executeQuery()) {
                while (polozkyRs.next()) {
                    Pridavek polozka = new Pridavek();
                    polozka.setId(polozkyRs.getInt("id"));
                    polozka.setNazev(polozkyRs.getString("nazev"));
                    polozka.setCena(polozkyRs.getInt("cena"));

                    polozky.add(polozka);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } catch (Exception e) {
            return null;
        }

        return polozky;
    }

    @Override
    public boolean writeObjednavka(shared.Objednavka objednavka) throws RemoteException {
        try (Connection conn = Db.get().getConnection();
                Statement pridavkyvobjStmt = conn.createStatement();
                ResultSet pridavkyvobjRs = pridavkyvobjStmt
                        .executeQuery("SELECT MAX(pridavek_id) FROM polozkyvobjednavce");) {
            conn.setAutoCommit(false); // zacatek transakce

            // priprava dotazu s pozadavkem na poskytnuti doplnenych automaticky
            // generovanych klicu
            pridavkyvobjRs.next();
            int prevId = pridavkyvobjRs.getInt("MAX(pridavek_id)");
            prevId++;
            System.out.println("prevId:    " + prevId);

            try (java.sql.PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO objednavky (celkovaCena, casObjednavky) values (?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, objednavka.getCelkovaCena());
                stmt.setString(2, objednavka.getCasObjednavky().toString());
                System.out.println(objednavka.getCelkovaCena());
                System.out.println(objednavka.getCasObjednavky().toString());
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("Nepodaril se zapis objednavky");
                }

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    objednavka.setId(rs.getInt(1)); // ziskani vygenerovaneho id
                    System.out.println("ziskane id: " + objednavka.getId());
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }

            try (java.sql.PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO polozkyvobjednavce (objednavky_id, polozky_id, mnozstvi, pridavek_id) values (?, ?, ?, ?)")) {

                int prevObjId = 0;
                int prevPolozkaId = 0;
                int mnozstvi = 1;
                for (Polozka polozka : objednavka.getPolozky()) {

                    System.out.println("prevObjId: " + prevObjId + "\t" + objednavka.getId());

                    System.out.println("prevPolozkaId: " + prevPolozkaId + "\t" + polozka.getId());

                    if (prevObjId == objednavka.getId() && prevPolozkaId == polozka.getId()) {
                        mnozstvi++;
                    }

                    prevObjId = objednavka.getId();
                    prevPolozkaId = polozka.getId();

                    System.out.print(polozka.getNazev() + "\t");
                    stmt.setInt(1, objednavka.getId());
                    stmt.setInt(2, polozka.getId());
                    stmt.setInt(3, mnozstvi);
                    stmt.setInt(4, prevId);
                    if (stmt.executeUpdate() != 1) {
                        throw new Exception("Nepodaril se zapis objednavky");
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                conn.rollback(); // zamutnuti trannsakce, odvolani zmen
                throw e;
            }

            try (java.sql.PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO pridavkyvobjednavce (pridavek_id, id_pridavku, polozka_id) values (?, ?,?)")) {

                for (Polozka polozka : objednavka.getPolozky()) {

                    System.out.println(polozka.getPridavkySize());

                    System.out.println(polozka.getPridavkyList().toString());
                    for (Pridavek pridavek : polozka.getPridavkyList()) {
                        System.out.println("nazev: " + pridavek.getNazev());
                        stmt.setInt(1, prevId);
                        stmt.setInt(2, pridavek.getId());
                        stmt.setInt(3, polozka.getId());
                        if (stmt.executeUpdate() != 1) {
                            throw new Exception("Nepodaril se zapis objednavky");
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }

            conn.commit(); // potvrzeni transakce
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public Objednavka getObjednavka(int id) throws RemoteException {
        Objednavka objednavka = new Objednavka();

        objednavka.setId(id);

        try (Connection conn = Db.get().getConnection();
                PreparedStatement pridavkyStmt = conn.prepareStatement(
                        "SELECT polozkyvobjednavce.polozky_id, polozky.nazev, polozky.cena, pridavky.id, pridavky.nazev, pridavky.cena FROM objednavky JOIN polozkyvobjednavce ON polozkyvobjednavce.objednavky_id=objednavky.id JOIN polozky ON polozkyvobjednavce.polozky_id=polozky.id LEFT JOIN pridavkyvobjednavce ON polozkyvobjednavce.pridavek_id = pridavkyvobjednavce.pridavek_id AND polozkyvobjednavce.polozky_id = pridavkyvobjednavce.polozka_id LEFT JOIN pridavky ON pridavkyvobjednavce.id_pridavku = pridavky.id where objednavky.id = ?");) {

            pridavkyStmt.setInt(1, id);

            try (ResultSet polozkyRs = pridavkyStmt.executeQuery()) {

                while (polozkyRs.next()) {
                    Polozka polozka = new Polozka();
                    polozka.setId(polozkyRs.getInt("polozkyvobjednavce.polozky_id"));
                    polozka.setNazev(polozkyRs.getString("polozky.nazev"));
                    polozka.setCena(polozkyRs.getInt("polozky.cena"));
                    Pridavek pridavek = new Pridavek();
                    pridavek.setId(polozkyRs.getInt("pridavky.id"));
                    pridavek.setCena(polozkyRs.getInt("pridavky.cena"));
                    pridavek.setNazev(polozkyRs.getString("pridavky.nazev"));

                    polozka.pridej(pridavek);

                    objednavka.pridej(polozka);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } catch (Exception e) {
            return null;
        }

        return objednavka;

    }

    @Override
    public int getPrevId() throws RemoteException {
        try (Connection conn = Db.get().getConnection();
                Statement pridavkyvobjStmt = conn.createStatement();
                ResultSet pridavkyvobjRs = pridavkyvobjStmt
                        .executeQuery("SELECT MAX(id) FROM objednavky");) {
            conn.setAutoCommit(false); // zacatek transakce

            // priprava dotazu s pozadavkem na poskytnuti doplnenych automaticky
            // generovanych klicu
            pridavkyvobjRs.next();
            int prevId = pridavkyvobjRs.getInt("MAX(id)");
            return prevId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}