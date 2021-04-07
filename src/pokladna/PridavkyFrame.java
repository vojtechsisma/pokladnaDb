package pokladna;

import javax.swing.JFrame;

import shared.Polozky;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.*;
import java.awt.*;

import shared.Polozka;
import shared.Pridavek;

public class PridavkyFrame extends JFrame {

        private static final long serialVersionUID = 1L;

        PridavkyFrame(Polozka p) {
                initPridavky(p);
        }

        int slanina = 0;
        int syr = 0;

        private void initPridavky(Polozka p) {

                JPanel jPanel1 = new JPanel();
                java.awt.GridLayout gl = new java.awt.GridLayout();
                jPanel1.setLayout(gl);

                int i = 0;
                try {
                        Polozky polozky = (Polozky) Naming.lookup("rmi://pokladna:12345/polozky");
                        for (Pridavek polozka : polozky.getPridavky(p.getId())) {
                                JButton btn = new JButton();
                                btn.setText(polozka.getNazev());
                                btn.setPreferredSize(new Dimension(200, 100));
                                btn.addActionListener(new java.awt.event.ActionListener() {
                                        public void actionPerformed(java.awt.event.ActionEvent evt) {

                                                Pridavek pr = new Pridavek();
                                                pr.setId(polozka.getId());
                                                pr.setNazev(polozka.getNazev());
                                                pr.setCena(polozka.getCena());
                                                p.pridej(pr);

                                        }
                                });
                                jPanel1.add(btn);
                                i++;
                        }

                } catch (MalformedURLException | RemoteException | NotBoundException e) {
                        e.printStackTrace();
                }

                JButton okButton = new JButton();
                okButton.setText("OK");
                okButton.setPreferredSize(new Dimension(200, 100));
                okButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                ObjednavkaFrame.zapis(p);
                                dispose();
                        }
                });
                jPanel1.add(okButton);
                gl.setRows(i + 1);

                add(jPanel1);
                setMinimumSize(new Dimension(500, 400));
                setTitle("Přídavky");

                setLocationRelativeTo(null);

                setSize(1800, 600);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                pack();

        }
}
