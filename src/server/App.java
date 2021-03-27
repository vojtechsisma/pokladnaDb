package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.SQLException;

import server.db.Db;

public class App {
    public static void main(String[] args) {
        try {
            Registry reg = LocateRegistry.createRegistry(12345);
            reg.rebind("polozky", new Polozky());

            System.out.println("Server ready");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
