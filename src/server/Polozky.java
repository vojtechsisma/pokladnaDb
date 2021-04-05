package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import server.db.Db;
import shared.Polozka;
import shared.Pridavek;

public class Polozky extends UnicastRemoteObject implements shared.Polozky {

    private static final long serialVersionUID = 1L;

    protected Polozky() throws RemoteException {
        super();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            conn.setAutoCommit(false);

            pridavkyvobjRs.next();
            int prevId = pridavkyvobjRs.getInt("MAX(pridavek_id)");
            prevId++;

            try (java.sql.PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO objednavky (celkovaCena, casObjednavky) values (?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, objednavka.getCelkovaCena());
                stmt.setString(2, objednavka.getCasObjednavky().toString());
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("Nepodaril se zapis objednavky");
                }

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    objednavka.setId(rs.getInt(1));

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

                    if (prevObjId == objednavka.getId() && prevPolozkaId == polozka.getId()) {
                        mnozstvi++;
                    }

                    prevObjId = objednavka.getId();
                    prevPolozkaId = polozka.getId();

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
                conn.rollback();
                throw e;
            }

            try (java.sql.PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO pridavkyvobjednavce (pridavek_id, id_pridavku, polozka_id, mnozstvi) values (?, ?,?, ?)")) {

                for (Polozka polozka : objednavka.getPolozky()) {

                    int prevObjId = 0;
                    int prevPolozkaId = 0;
                    int prevPridavekId = 0;
                    int mnozstvi = 1;
                    for (Pridavek pridavek : polozka.getPridavkyList()) {

                        if (prevObjId == objednavka.getId() && prevPolozkaId == polozka.getId() && prevPridavekId == pridavek.getId()) {
                            mnozstvi++;
                        }

                        prevObjId = objednavka.getId();
                        prevPolozkaId = polozka.getId();
                        prevPridavekId = pridavek.getId();

                        stmt.setInt(1, prevId);
                        stmt.setInt(2, pridavek.getId());
                        stmt.setInt(3, polozka.getId());
                        stmt.setInt(4, mnozstvi);
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

            conn.commit();
            System.out.println("Zapsana objednavka id: " + objednavka.getId());
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
                        "SELECT polozkyvobjednavce.polozky_id, polozky.nazev, polozky.cena, pridavky.id, pridavky.nazev, pridavky.cena,  pridavkyvobjednavce.polozka_id FROM objednavky JOIN polozkyvobjednavce ON polozkyvobjednavce.objednavky_id=objednavky.id JOIN polozky ON polozkyvobjednavce.polozky_id=polozky.id LEFT JOIN pridavkyvobjednavce ON polozkyvobjednavce.pridavek_id = pridavkyvobjednavce.pridavek_id AND polozkyvobjednavce.polozky_id = pridavkyvobjednavce.polozka_id LEFT JOIN pridavky ON pridavkyvobjednavce.id_pridavku = pridavky.id where objednavky.id = ?");) {

            pridavkyStmt.setInt(1, id);

            try (ResultSet polozkyRs = pridavkyStmt.executeQuery()) {

                int prevObjId = 0;
                int prevPolozkaId = 0;
                int mnozstvi = 1;

                Polozka polozka = null;
                while (polozkyRs.next()) {
                    Pridavek pridavek = new Pridavek();

                    if (prevObjId == objednavka.getId()
                            && prevPolozkaId == polozkyRs.getInt("pridavkyvobjednavce.polozka_id")
                            && polozkyRs.getInt("pridavkyvobjednavce.polozka_id") != 0) {
                        mnozstvi++;
                    } else {
                        polozka = new Polozka();
                        mnozstvi = 1;

                        polozka.setId(polozkyRs.getInt("polozkyvobjednavce.polozky_id"));
                        polozka.setNazev(polozkyRs.getString("polozky.nazev"));
                        polozka.setCena(polozkyRs.getInt("polozky.cena"));
                    }

                    pridavek.setId(polozkyRs.getInt("pridavky.id"));
                    pridavek.setCena(polozkyRs.getInt("pridavky.cena"));
                    pridavek.setNazev(polozkyRs.getString("pridavky.nazev"));
                    prevObjId = objednavka.getId();
                    prevPolozkaId = polozkyRs.getInt("pridavkyvobjednavce.polozka_id");

                    polozka.pridej(pridavek);

                    if (mnozstvi == 1) {
                        objednavka.pridej(polozka);
                    }
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
                ResultSet pridavkyvobjRs = pridavkyvobjStmt.executeQuery("SELECT MAX(id) FROM objednavky");) {
            conn.setAutoCommit(false);

            pridavkyvobjRs.next();
            int prevId = pridavkyvobjRs.getInt("MAX(id)");
            return prevId;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean writePolozka(String nazev, int cena) throws RemoteException {
        try (Connection conn = Db.get().getConnection();) {
            conn.setAutoCommit(false);

            try (java.sql.PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO polozky (nazev, cena) values (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nazev);
                stmt.setInt(2, cena);
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("Nepodaril se zapis polozky");
                }
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean writePridavek(int parent, String nazev, int cena) throws RemoteException {
        int idPridavku = 0;
        try (Connection conn = Db.get().getConnection();) {
            conn.setAutoCommit(false);

            try (java.sql.PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO pridavky (nazev, cena) values (?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, nazev);
                stmt.setInt(2, cena);
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("Nepodaril se zapis pridavku");
                }

                ResultSet rs = stmt.getGeneratedKeys();

                if (rs.next()) {
                    idPridavku = rs.getInt(1);
                }
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }

            try (java.sql.PreparedStatement stmt = conn
                    .prepareStatement("INSERT INTO pridavkyvpolozce (polozky_id, pridavky_id) values (?, ?)")) {

                stmt.setInt(1, parent);
                stmt.setInt(2, idPridavku);
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("Nepodaril se zapis pridavku");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }
            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Pridavek> getPridavkyAll() throws RemoteException {
        List<Pridavek> polozky = new ArrayList<>();

        try (Connection conn = Db.get().getConnection();
                Statement polozkyStmt = conn.createStatement();
                ResultSet polozkyRs = polozkyStmt.executeQuery("SELECT id, nazev, cena FROM pridavky");) {

            while (polozkyRs.next()) {
                Pridavek polozka = new Pridavek();
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
    public Polozka getPolozka(int id) throws RemoteException {
        try (Connection conn = Db.get().getConnection();) {
            conn.setAutoCommit(false);
            Polozka polozka = null;

            try (java.sql.PreparedStatement stmt = conn
                    .prepareStatement("SELECT id, nazev, cena FROM polozky WHERE id = ?")) {
                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        polozka = new Polozka();
                        polozka.setId(rs.getInt(1));
                        polozka.setNazev(rs.getString(2));
                        polozka.setCena(rs.getInt(3));
                    }
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }

            conn.commit();
            return polozka;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Pridavek getPridavek(int id) throws RemoteException {
        try (Connection conn = Db.get().getConnection();) {
            conn.setAutoCommit(false);
            Pridavek polozka = null;

            try (java.sql.PreparedStatement stmt = conn
                    .prepareStatement("SELECT id, nazev, cena FROM pridavky WHERE id = ?")) {
                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        polozka = new Pridavek();
                        polozka.setId(rs.getInt(1));
                        polozka.setNazev(rs.getString(2));
                        polozka.setCena(rs.getInt(3));
                    }
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }

            try (java.sql.PreparedStatement stmt = conn
                    .prepareStatement("SELECT polozky_id, pridavky_id FROM pridavkyvpolozce WHERE pridavky_id = ?")) {
                stmt.setInt(1, id);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        polozka.setParent(rs.getInt(1));
                    }
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }

            conn.commit();
            return polozka;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updatePolozka(Polozka polozka) throws RemoteException {
        try (Connection conn = Db.get().getConnection();) {
            conn.setAutoCommit(false);

            try (java.sql.PreparedStatement stmt = conn
                    .prepareStatement("UPDATE polozky SET nazev=?, cena=? WHERE id = ?")) {
                stmt.setString(1, polozka.getNazev());
                stmt.setInt(2, polozka.getCena());
                stmt.setInt(3, polozka.getId());
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("Nepodaril se uprava polozky");
                }
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updatePridavek(Pridavek pridavek) throws RemoteException {
        try (Connection conn = Db.get().getConnection();) {
            conn.setAutoCommit(false);

            try (java.sql.PreparedStatement stmt = conn
                    .prepareStatement("UPDATE pridavky SET nazev=?, cena=? WHERE id = ?")) {
                stmt.setString(1, pridavek.getNazev());
                stmt.setInt(2, pridavek.getCena());
                stmt.setInt(3, pridavek.getId());
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("Nepodaril se uprava pridavku");
                }
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }
            try (java.sql.PreparedStatement stmt = conn
                    .prepareStatement("UPDATE pridavkyvpolozce SET polozky_id=? WHERE pridavky_id = ?")) {
                stmt.setInt(1, pridavek.getParent());
                stmt.setInt(2, pridavek.getId());
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("Nepodaril se uprava pridavku");
                }
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePolozka(int id) throws RemoteException {
        try (Connection conn = Db.get().getConnection();) {
            conn.setAutoCommit(false);

            try (java.sql.PreparedStatement stmt = conn.prepareStatement("DELETE FROM polozky  WHERE id = ?")) {
                stmt.setInt(1, id);
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("Nepodaril se smazani polozky");
                }
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }
            try (java.sql.PreparedStatement stmt = conn
                    .prepareStatement("DELETE FROM pridavkyvpolozce WHERE polozky_id = ?")) {
                stmt.setInt(1, 1);
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("Nepodaril se smazani polozky");
                }
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deletePridavek(int id) throws RemoteException {
        try (Connection conn = Db.get().getConnection();) {
            conn.setAutoCommit(false);

            try (java.sql.PreparedStatement stmt = conn.prepareStatement("DELETE FROM pridavky  WHERE id = ?")) {
                stmt.setInt(1, id);
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("Nepodaril se smazani pridavku");
                }
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }
            try (java.sql.PreparedStatement stmt = conn
                    .prepareStatement("DELETE FROM pridavkyvpolozce WHERE pridavky_id = ?")) {
                stmt.setInt(1, id);
                if (stmt.executeUpdate() != 1) {
                    throw new Exception("Nepodaril se smazani pridavku");
                }
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw e;
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}