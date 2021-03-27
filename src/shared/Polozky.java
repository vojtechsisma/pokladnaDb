package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Polozky extends Remote {
    List<Polozka> getPolozky() throws RemoteException;
    List<Polozka> getPridavky() throws RemoteException;
}