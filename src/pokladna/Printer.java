package pokladna;

import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedList;

import shared.Objednavka;
import shared.Polozka;
import shared.Pridavek;

public class Printer {
    static Printer instance = null;

    public static Printer getInstance() {
        if (instance == null) {
            instance = new Printer();
        }
        return instance;
    }

    public static void tiskni(Objednavka o) throws RemoteException {
        System.out.println("id objednavky " + o.getId());
        try (FileWriter fw = new FileWriter("objednavka_" + o.getId() + ".txt")) {

            fw.write("----------------------");
            fw.write("\n     /\\        /\\");
            fw.write("\n    /  \\      /  \\");
            fw.write("\n   /    \\    /    \\");
            fw.write("\n  /      \\  /      \\");
            fw.write("\n /        \\/        \\");
            fw.write("\n/                    \\");
            fw.write("\n---------0" + o.getId() + "-----------\n");

            for (Polozka polozka : o.getPolozky()) {

                String nazev = polozka.getNazev();
                int cena = polozka.getCena();
                LinkedList<Pridavek> pridavky = new LinkedList<Pridavek>();
                fw.append(nazev + "\t" + polozka.getZakladniCena() + "\n\t");

                if (polozka.getPridavkyList().size() > 0) {
                    for (int i = 0; i < polozka.getPridavkySize(); i++) {
                        pridavky.add(polozka.getPridavky(i));
                    }

                    for (Pridavek pridavek : pridavky) {
                        if (pridavek.getNazev() != null) {
                            fw.append("+" + pridavek.getNazev() + "\t" + pridavek.getCena() + "\n\t");
                        }
                    }
                }

                fw.append("----\n\t" + cena + "\n");

            }

            fw.write("----------------------\n");
            fw.write("Celkem: " + Integer.toString(o.getCelkovaCena()));

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
