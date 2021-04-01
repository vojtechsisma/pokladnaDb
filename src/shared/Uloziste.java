package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Uloziste extends Remote{



	int getPocet() throws RemoteException;

	Objednavka getObjednavka(int index) throws RemoteException;
	void pridej(Objednavka objednavka) throws RemoteException;

	void odeber(int id) throws RemoteException;


}
