package pokladna;

import java.awt.LayoutManager;
import java.net.MalformedURLException;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import java.awt.FlowLayout;

import shared.Polozka;
import shared.Polozky;

public class ObjednavkaFrame<E> extends javax.swing.JFrame {

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
        private static javax.swing.JTextArea jTextArea2;
        private static javax.swing.JTextArea CenaTextArea;
        // public Objednavka obj = new Objednavka();
        // public Uloziste u = Uloziste.getInstance();
        // public Uloziste u = Uloziste.getInstance();

        private void initComponents() {

                JPanel jPanel1 = new JPanel();
                GridLayout gl = new GridLayout();
                jPanel1.setLayout(gl);

                int i = 0;
                Map<String, JButton> buttons = new HashMap<String, JButton>();
                try {
                        Polozky polozky = (Polozky) Naming.lookup("rmi://pokladna:12345/polozky");
                        for (Polozka polozka : polozky.getPolozky()) {
                                JButton btn = new JButton();
                                btn.setText(polozka.getNazev());
                                btn.setPreferredSize(new Dimension(200, 100));
                                btn.addActionListener(new java.awt.event.ActionListener() {
                                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                /*
                                                 * MenuPridavky m = new MenuPridavky(obj); m.setVisible(true);
                                                 * m.setLocation(200, 200);
                                                 */
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
                gl.setRows(i);


                // jPanel1 = new javax.swing.JPanel();
                JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
                JTextArea jTextArea2 = new javax.swing.JTextArea();
                JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
                JTextArea jTextArea3 = new javax.swing.JTextArea();
                JButton jButton3 = new javax.swing.JButton();
                JButton jButton4 = new javax.swing.JButton();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                jTextArea2.setColumns(20);
                jTextArea2.setRows(5);
                jScrollPane2.setViewportView(jTextArea2);
                jTextArea2.setEditable(false);

                jTextArea3.setColumns(20);
                jTextArea3.setRows(1);
                jTextArea3.setEditable(false);
                jScrollPane3.setViewportView(jTextArea3);

                jButton3.setText("OK");

                jButton4.setText("storno");

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
                setSize(1800, 600);
                setLocationRelativeTo(null);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                pack();

        }// </editor-fold>

        public void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
                System.out.println("oojoi");
                dispose();
        }

        public void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {

                dispose();
        }

        /**
         *
         */
        static int c = 0;

        public static void zapis(String nazev, int cena) {

                jTextArea2.append(nazev + "\n\t----\n\t" + cena + "\n");
                c += cena;
                CenaTextArea.setText("Celkem: " + Integer.toString(c));
        }
}
