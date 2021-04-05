package admin;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import shared.Polozka;
import shared.Polozky;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class PolozkaFrame extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    public PolozkaFrame(int idPolozky) {
        initComponents(idPolozky);
    }

    Polozka p = null;

    private void initComponents(int idPolozky) {

        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JTextField nazevTextField = new JTextField();
        JTextField cenaTextField = new JTextField();
        JButton okButton = new JButton();
        JButton stornoButton = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        try {
            Polozky polozky = (Polozky) Naming.lookup("rmi://pokladna:12345/polozky");

            if (idPolozky != 0) {
                p = polozky.getPolozka(idPolozky);
                nazevTextField.setText(p.getNazev());
                cenaTextField.setText(Integer.toString(p.getCena()));
            }
        } catch (MalformedURLException | RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

        setLocationRelativeTo(null);
        jLabel1.setText("Název");

        jLabel2.setText("Cena");

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
                        JOptionPane.showMessageDialog(null,
                                "Zadáno neplatné heslo - musí obsahovat velká i malá písmena, číslice i speciální znaky a délku alespoň 12 znaků.",
                                "Upozornění", JOptionPane.WARNING_MESSAGE);
                        passed = false;
                    }
                    if (passed) {
                        if (idPolozky != 0) {
                            p.setCena(Integer.parseInt(cena));
                            p.setNazev(nazev);
                            polozky.updatePolozka(p);
                        } else {
                            polozky.writePolozka(nazev, Integer.parseInt(cena));
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
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
                .createSequentialGroup().addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup().addComponent(jLabel1).addGap(18, 18, 18)
                                        .addComponent(nazevTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 101,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup().addComponent(jLabel2).addGap(23, 23, 23)
                                        .addComponent(cenaTextField)))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stornoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)));
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(okButton)
                        .addComponent(stornoButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
    }

}
