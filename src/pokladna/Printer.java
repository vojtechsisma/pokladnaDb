package pokladna;

import java.io.FileWriter;
import java.io.IOException;

public class Printer {
    static Printer instance = null;

    public static Printer getInstance() {
        if (instance == null) {
            instance = new Printer();
        }
        return instance;
    }

    public static void tiskni(int index) {
        System.out.println("id objednavky " + index);
        try (FileWriter fw = new FileWriter("objednavka_" + index + ".txt")) {

            Uloziste u = Uloziste.getInstance();
            Objednavka o = null;
            fw.write("----------------------");
            fw.write("\n     /\\        /\\");
            fw.write("\n    /  \\      /  \\");
            fw.write("\n   /    \\    /    \\");
            fw.write("\n  /      \\  /      \\");
            fw.write("\n /        \\/        \\");
            fw.write("\n/                    \\");
            fw.write("\n---------0" + index + "-----------\n");

            o = (Objednavka) u.getObjednavka(index);
            for (int i = 0; i < o.getPocet(); i++) {
                System.out.println(o.getObjednavka(i).getNazev());
                System.out.println(o.getObjednavka(i).getCena());
            }
            for (int i = 0; i < o.getPocet(); i++) {
                fw.write(o.getObjednavka(i).getNazev());
                fw.append("\n\t-----\n\t" + Integer.toString(o.getObjednavka(i).getCena()) + "\n");
            }
            fw.write("----------------------\n");
            fw.write("Celkem: " + Integer.toString(o.getCelkovaCena()));

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}
