package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class App {
    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.createRegistry(12345);

            reg.rebind("objednavka", new Objednavka());
            reg.rebind("polozky", new Polozky());
            reg.rebind("uloziste", new Uloziste());

            System.out.println("Server běží");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
