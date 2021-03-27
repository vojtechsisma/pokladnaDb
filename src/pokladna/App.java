package pokladna;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import shared.Polozka;
import shared.Polozky;

public class App {
    public static void main(String[] args) {
        MainFrame.main(args);




        /* try {
            Polozky polozky = (Polozky) Naming.lookup("rmi://pokladna:12345/polozky");
            for (Polozka polozka : polozky.getPolozky()) {
                System.out.print(polozka.getId() + "\t");
                System.out.print(polozka.getNazev() + "\t");
                System.out.println(polozka.getCena());
            }
            for (Polozka polozka : polozky.getPridavky()) {
                System.out.print(polozka.getId() + "\t");
                System.out.print(polozka.getNazev() + "\t");
                System.out.println(polozka.getCena());
            }
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 */
           
    }
}
