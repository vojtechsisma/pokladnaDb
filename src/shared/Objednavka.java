package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDateTime;

public interface Objednavka extends Remote {

    public int getId() throws RemoteException;

    public void setId(int id) throws RemoteException;

    public LocalDateTime getCasObjednavky() throws RemoteException;

    public int getCena() throws RemoteException;

    public void setCena(int cena) throws RemoteException;

    public void pridej(Polozka polozka) throws RemoteException;

    public int getCelkovaCena() throws RemoteException;
    
    public void vypis() throws RemoteException;
}