package pokladna;

import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;

import shared.Objednavka;
import shared.Polozka;
import shared.Polozky;
import shared.Pridavek;
import shared.Uloziste;

public class ObjednavkaFrame extends javax.swing.JFrame {

        /**
        *
        */
        private static final long serialVersionUID = 1L;
        int i = 0;

        // <editor-fold defaultstate="collapsed" desc="Generated Code">
        public ObjednavkaFrame() {

                initComponents();
        }

        int slanina = 0;
        int syr = 0;

        JScrollPane jScrollPane2 = new JScrollPane();
        private static JTextArea jTextArea2 = new JTextArea();
        JScrollPane jScrollPane3 = new JScrollPane();
        private static JTextArea jTextArea3 = new JTextArea();
        JButton jButton3 = new JButton();
        JButton jButton4 = new JButton();

        // public Objednavka obj = new Objednavka();
        // public Uloziste u = Uloziste.getInstance();
        // public Uloziste u = Uloziste.getInstance();
        private Polozky polozky;
        private Uloziste uloziste;
        private static Objednavka objednavka;
        static int c = 0;

        private void initComponents() {

                c = 0;
                polozky = null;
                objednavka = null;

                JPanel jPanel1 = new JPanel();
                GridLayout gl = new GridLayout(1, 1, 5, 5);
                jPanel1.setLayout(gl);

                int i = 0;
                try {
                        polozky = (Polozky) Naming.lookup("rmi://pokladna:12345/polozky");
                        objednavka = (Objednavka) Naming.lookup("rmi://pokladna:12345/objednavka");

                        uloziste = (Uloziste) Naming.lookup("rmi://pokladna:12345/uloziste");
                        objednavka.obnov();
                        setTitle("Nová objednávka id: " + (polozky.getPrevId() + 1));
                        objednavka.setId(polozky.getPrevId() + 1);
                        for (Polozka polozka : polozky.getPolozky()) {
                                JButton btn = new JButton();
                                btn.setText(polozka.getNazev());
                                btn.setPreferredSize(new Dimension(200, 100));
                                btn.addActionListener(new java.awt.event.ActionListener() {
                                        public void actionPerformed(java.awt.event.ActionEvent evt) {

                                                Polozka p = new Polozka();
                                                p.setNazev(polozka.getNazev());
                                                p.setCena(polozka.getCena());
                                                p.setId(polozka.getId());

                                                PridavkyFrame pridavky = new PridavkyFrame(p);
                                                pridavky.setVisible(true);

                                        }
                                });
                                jPanel1.add(btn);
                                i++;
                        }

                } catch (MalformedURLException | RemoteException | NotBoundException e) {
                        e.printStackTrace();
                }
                gl.setRows(i);

                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setMinimumSize(new Dimension(550, 400));
                setLocationRelativeTo(null);

                jTextArea2.setColumns(20);
                jTextArea2.setRows(5);
                jScrollPane2.setViewportView(jTextArea2);
                jTextArea2.setEditable(false);
                jTextArea2.setLineWrap(true);
                jTextArea2.setWrapStyleWord(true);

                jTextArea2.setText("");
                jTextArea3.setText("");

                jTextArea3.setColumns(20);
                jTextArea3.setRows(1);
                jTextArea3.setEditable(false);
                jScrollPane3.setViewportView(jTextArea3);
                jTextArea3.setLineWrap(true);
                jTextArea3.setWrapStyleWord(true);

                jButton3.setText("OK");
                jButton3.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {

                                try {

                                        uloziste.pridej(objednavka);
                                        polozky.writeObjednavka(objednavka);
                                        dispose();
                                } catch (RemoteException e) {
                                        e.printStackTrace();
                                }

                        }
                });

                jButton4.setText("storno");
                jButton4.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                dispose();
                        }
                });

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup().addContainerGap()
                                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup().addComponent(
                                                                                jButton3,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                105,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addPreferredGap(
                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(jButton4,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                85, Short.MAX_VALUE))
                                                                .addComponent(jScrollPane2).addComponent(jScrollPane3))
                                                .addContainerGap()));
                layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                                javax.swing.GroupLayout.Alignment.TRAILING,
                                layout.createSequentialGroup().addContainerGap().addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jScrollPane2,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                228, Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jScrollPane3,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                24,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jButton3)
                                                                                .addComponent(jButton4)))
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap()));
                setPreferredSize(new Dimension(600, 500));
                setLocationRelativeTo(null);

                pack();

        }

        public static void zapis(Polozka polozka) {

                try {
                        objednavka.pridej(polozka);
                } catch (RemoteException e) {
                        e.printStackTrace();
                }

                LinkedList<Pridavek> pridavky = new LinkedList<Pridavek>();

                String nazev = polozka.getNazev();
                int cena = polozka.getCena();
                for (int i = 0; i < polozka.getPridavkySize(); i++) {
                        pridavky.add(polozka.getPridavky(i));
                }

                jTextArea2.append(nazev + "\t" + polozka.getZakladniCena() + "\n\t");
                for (Pridavek pridavek : pridavky) {
                        jTextArea2.append("+" + pridavek.getNazev() + "\t" + pridavek.getCena() + "\n\t");
                }
                jTextArea2.append("----\n\t" + cena + "\n");
                jTextArea2.setCaretPosition(jTextArea2.getDocument().getLength());
                c += cena;
                jTextArea3.setText("Celkem: " + Integer.toString(c));
        }

}
