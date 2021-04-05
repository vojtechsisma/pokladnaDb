package admin;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

import java.awt.Dimension;
import java.awt.print.PrinterException;

import shared.Objednavka;
import shared.Polozka;
import shared.Polozky;
import shared.Pridavek;
import shared.Uloziste;

public class VypisFrame extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    public VypisFrame() {
        initComponents();
    }

    private void initComponents() {

        JButton jButton1 = new JButton();
        JSpinner jSpinner1 = new JSpinner();
        JLabel jLabel1 = new JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        setLocationRelativeTo(null);
        jSpinner1.setPreferredSize(new Dimension(50, 20));
        jSpinner1.setValue(1);

        jButton1.setText("vypsat");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Integer cisloObj = (Integer) jSpinner1.getValue();
                try {
                    vypis(cisloObj);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });

        jLabel1.setText("Číslo objednávky:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup().addGap(35, 35, 35).addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(jButton1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
    }

    public void vypis(int index) throws RemoteException {
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (o == null) {
            o = polozky.getObjednavka(index);

        }

        Printer p = new Printer();
        p.tiskni(index);

        JTextArea area = new JTextArea();
        JTextArea area2 = new JTextArea();
        JFrame frame = null;
        try {

            frame = new JFrame();
            frame.setVisible(true);
            frame.setTitle("Objednávka: " + (index));
            frame.setLocationRelativeTo(null);

            int celkovaCena = 0;
            for (Polozka polozka : o.getPolozky()) {
                int cPolozky = 0;
                String nazev = polozka.getNazev();
                cPolozky = polozka.getZakladniCena();
                celkovaCena += polozka.getZakladniCena();
                LinkedList<Pridavek> pridavky = new LinkedList<Pridavek>();
                area.append(nazev + "\t" + polozka.getZakladniCena() + "\n\t");

                for (int i = 0; i < polozka.getPridavkySize(); i++) {
                    pridavky.add(polozka.getPridavky(i));
                }

                for (Pridavek pridavek : pridavky) {
                    if (pridavek.getNazev() != null) {
                        area.append("+" + pridavek.getNazev() + "\t" + pridavek.getCena() + "\n\t");
                        celkovaCena += pridavek.getCena();
                        cPolozky += pridavek.getCena();
                    }

                }

                area.append("----\n\t" + cPolozky + "\n");
                area.setCaretPosition(area.getDocument().getLength());

            }

            area2.append("Celkem: " + celkovaCena);
            area2.setEditable(false);
            area.setEditable(false);

            String[] options = { "ano", "ne" };
            int result = JOptionPane.showOptionDialog(frame, "Chcete objednávku vytisknout na fyzické tiskárně??",
                    "Tisk", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, // no custom icon
                    options, // button titles
                    options[0] // default button
            );
            if (result == JOptionPane.YES_OPTION) {
                try {
                    area.print();
                } catch (PrinterException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Chyba", "Problém s fyzickou tiskárnou",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

            JScrollPane jScrollPane1 = new JScrollPane();

            frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.setLocation(200, 200);

            area.setColumns(20);
            area.setRows(10);
            area2.setColumns(20);
            area2.setRows(5);
            jScrollPane1.setViewportView(area);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(frame.getContentPane());
            frame.getContentPane().setLayout(layout);
            layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup().addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380,
                                            Short.MAX_VALUE)
                                    .addComponent(area2, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addContainerGap()));
            layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup().addContainerGap()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 191,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(area2, javax.swing.GroupLayout.PREFERRED_SIZE, 18,
                                    javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

            frame.pack();

        } catch (IndexOutOfBoundsException exc) {
            JOptionPane.showMessageDialog(this, "Taková objednávka neexistuje!!", "Chyba uživatele",
                    JOptionPane.ERROR_MESSAGE);
            frame.dispose();
        }

    }
}