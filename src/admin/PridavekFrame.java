package admin;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import shared.Polozka;
import shared.Polozky;
import shared.Pridavek;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class PridavekFrame extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    public PridavekFrame(int idPolozky) {
        initComponents(idPolozky);
    }

    Pridavek p = null;

    private void initComponents(int idPolozky) {

        setResizable(false);
        setLocationRelativeTo(null);
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        JTextField nazevTextField = new JTextField();
        JTextField cenaTextField = new JTextField();
        JButton okButton = new JButton();
        JButton stornoButton = new JButton();
        JComboBox<String> jComboBox1 = new JComboBox<String>();

        try {
            Polozky polozky = (Polozky) Naming.lookup("rmi://pokladna:12345/polozky");
            for (Polozka polozka : polozky.getPolozky()) {
                jComboBox1.addItem(polozka.getId() + " " + polozka.getNazev());
            }
            if (idPolozky != 0) {
                p = polozky.getPridavek(idPolozky);
                nazevTextField.setText(p.getNazev());
                cenaTextField.setText(Integer.toString(p.getCena()));
                jComboBox1.setSelectedIndex(p.getParent() - 1);
            }
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Název");

        jLabel2.setText("Cena");

        jLabel3.setText("Položka");

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                try {
                    Polozky polozky = (Polozky) Naming.lookup("rmi://pokladna:12345/polozky");
                    boolean passed = true;
                    String nazev = nazevTextField.getText();
                    String cena = cenaTextField.getText();
                    final Pattern nazevPattern = Pattern.compile("[A-ZÁ-Ža-zá-ž]", Pattern.CASE_INSENSITIVE);

                    Matcher matcher = nazevPattern.matcher(nazev);

                    if (matcher.find()) {
                        passed = true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Zadán neplatný název", "Upozornění",
                                JOptionPane.WARNING_MESSAGE);
                        passed = false;
                    }

                    final Pattern cenaPattern = Pattern.compile("[0-9]");

                    matcher = cenaPattern.matcher(cena);
                    if (matcher.find() && passed == true) {
                        passed = true;

                    } else {
                        JOptionPane.showMessageDialog(null, "Zadána neplatná cena.", "Upozornění",
                                JOptionPane.WARNING_MESSAGE);
                        passed = false;
                    }

                    String parent = (String) jComboBox1.getSelectedItem();
                    parent = parent.substring(0, 1);

                    if (passed) {
                        if (idPolozky != 0) {
                            p.setCena(Integer.parseInt(cena));
                            p.setNazev(nazev);
                            p.setParent(Integer.parseInt(parent));
                            polozky.updatePridavek(p);
                        } else {
                            polozky.writePridavek(Integer.parseInt(parent), nazev, Integer.parseInt(cena));
                        }

                        dispose();
                    }
                } catch (MalformedURLException | RemoteException | NotBoundException e) {
                    e.printStackTrace();
                }

            }
        });

        stornoButton.setText("Storno");
        stornoButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup().addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cenaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 101,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup().addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75,
                                                Short.MAX_VALUE)
                                        .addComponent(nazevTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 101,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup().addComponent(jLabel3)
                                                        .addGap(0, 0, Short.MAX_VALUE))
                                                .addComponent(okButton, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(stornoButton, javax.swing.GroupLayout.DEFAULT_SIZE, 102,
                                                        Short.MAX_VALUE)
                                                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel1)
                        .addComponent(nazevTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel2)
                        .addComponent(cenaTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(okButton)
                        .addComponent(stornoButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
    }
}
