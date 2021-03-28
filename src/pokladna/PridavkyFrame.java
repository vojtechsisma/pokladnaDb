package pokladna;

import javax.swing.JFrame;

import shared.Polozky;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import java.awt.*;

import shared.Objednavka;
import shared.Polozka;

public class PridavkyFrame extends JFrame {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        // public Objednavka obj = Objednavka.getInstance();

        PridavkyFrame(Polozka p) {
                initPridavky(p);
        }

        int slanina = 0;
        int syr = 0;

        private void initPridavky(Polozka p) {

                JPanel jPanel1 = new JPanel();
                GridLayout gl = new GridLayout();
                jPanel1.setLayout(gl);

                int i = 0;
                Map<String, JButton> buttons = new HashMap<String, JButton>();
                try {
                        Polozky polozky = (Polozky) Naming.lookup("rmi://pokladna:12345/polozky");
                        Objednavka objednavka = (Objednavka) Naming.lookup("rmi://pokladna:12345/objednavka");
                        for (Polozka polozka : polozky.getPridavky()) {
                                JButton btn = new JButton();
                                btn.setText(polozka.getNazev());
                                btn.setPreferredSize(new Dimension(200, 100));
                                btn.addActionListener(new java.awt.event.ActionListener() {
                                        public void actionPerformed(java.awt.event.ActionEvent evt) {

                                                p.pridej(polozka);

                                        }
                                });
                                jPanel1.add(btn);
                                buttons.put(polozka.getNazev(), new JButton());
                                i++;
                        }

                } catch (MalformedURLException | RemoteException | NotBoundException e) {
                        // TODO Auto-generated catch block
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

                // jPanel1 = new javax.swing.JPanel();
                add(jPanel1);
                setMinimumSize(new Dimension(500, 400));
                setTitle("Přídavky");

                setSize(1800, 600);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                pack();

        }// </editor-fold>
}
