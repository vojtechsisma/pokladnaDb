package admin;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;

import shared.Objednavka;
import shared.Polozka;
import shared.Polozky;
import shared.Pridavek;
import shared.Uloziste;

public class Printer {

    public void tiskni(int index) throws RemoteException {

        Polozky polozky = null;
        Uloziste uloziste = null;
        try {
            polozky = (Polozky) Naming.lookup("rmi://pokladna:12345/polozky");

            uloziste = (Uloziste) Naming.lookup("rmi://pokladna:12345/uloziste");
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        Objednavka o = null;
        try {
            o = uloziste.getObjednavka(index);

            System.out.println("vypis z uloziste");
        } catch (Exception e) {
            System.out.println("Objednavka neni v pameti, probehne hledani v databazi");
        }

        if (o == null) {
            o = polozky.getObjednavka(index);

            System.out.println("vypis z db");
        }

        try (FileWriter fw = new FileWriter("objednavka_" + o.getId() + ".txt")) {

            fw.write("----------------------");
            fw.write("\n     /\\        /\\");
            fw.write("\n    /  \\      /  \\");
            fw.write("\n   /    \\    /    \\");
            fw.write("\n  /      \\  /      \\");
            fw.write("\n /        \\/        \\");
            fw.write("\n/                    \\");
            fw.write("\n---------0" + o.getId() + "-----------\n");

            int celkovaCena = 0;
            for (Polozka polozka : o.getPolozky()) {
                int cPolozky = 0;
                String nazev = polozka.getNazev();
                cPolozky = polozka.getZakladniCena();
                celkovaCena += polozka.getZakladniCena();
                LinkedList<Pridavek> pridavky = new LinkedList<Pridavek>();
                fw.append(nazev + "\t" + polozka.getZakladniCena() + "\n\t");

                for (int i = 0; i < polozka.getPridavkySize(); i++) {
                    pridavky.add(polozka.getPridavky(i));
                }

                for (Pridavek pridavek : pridavky) {
                    if (pridavek.getNazev() != null) {
                        fw.append("+" + pridavek.getNazev() + "\t" + pridavek.getCena() + "\n\t");
                        celkovaCena += pridavek.getCena();
                        cPolozky += pridavek.getCena();
                    }

                }

                fw.append("----\n\t" + cPolozky + "\n");
            }

            fw.write("----------------------\n");
            fw.write("Celkem: " + celkovaCena);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
