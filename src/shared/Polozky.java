package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Polozky extends Remote {
    List<Polozka> getPolozky() throws RemoteException;

    List<Pridavek> getPridavky(int id) throws RemoteException;

    Objednavka getObjednavka(int id) throws RemoteException;

    boolean writeObjednavka(Objednavka objednavka) throws RemoteException;

    int getPrevId() throws RemoteException;

    boolean writePolozka(String nazev, int cena) throws RemoteException;

    boolean writePridavek(int parent, String nazev, int cena) throws RemoteException;

    List<Pridavek> getPridavkyAll() throws RemoteException;

    Polozka getPolozka(int id) throws RemoteException;

    Pridavek getPridavek(int id) throws RemoteException;

    boolean updatePolozka(Polozka polozka) throws RemoteException;

    boolean updatePridavek(Pridavek pridavek) throws RemoteException;

    boolean deletePolozka(int id) throws RemoteException;

    boolean deletePridavek(int id) throws RemoteException;
}