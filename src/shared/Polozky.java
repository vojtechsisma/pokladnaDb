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
}