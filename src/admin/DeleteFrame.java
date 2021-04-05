package admin;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import shared.Polozka;
import shared.Polozky;
import shared.Pridavek;

public class DeleteFrame extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    public DeleteFrame(boolean pridavek) {
        initComponents(pridavek);
    }

    private void initComponents(boolean pridavek) {

        JComboBox<String> jComboBox1 = new JComboBox<String>();
        JLabel jLabel1 = new JLabel();
        JButton okButton = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        setResizable(false);

        if (pridavek) {
            try {
                Polozky polozky = (Polozky) Naming.lookup("rmi://pokladna:12345/polozky");
                for (Pridavek polozka : polozky.getPridavkyAll()) {
                    jComboBox1.addItem(polozka.getId() + " " + polozka.getNazev());
                }
            } catch (MalformedURLException | RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Polozky polozky = (Polozky) Naming.lookup("rmi://pokladna:12345/polozky");
                for (Polozka polozka : polozky.getPolozky()) {
                    jComboBox1.addItem(polozka.getId() + " " + polozka.getNazev());
                }
            } catch (MalformedURLException | RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        }

        jLabel1.setText("Smazat");

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String id = (String) jComboBox1.getSelectedItem();
                id = id.substring(0, 1);
                String[] options = { "ano", "ne" };
                int result = JOptionPane.showOptionDialog(okButton,
                        "Chcete opravdu smazat polozku  " + jComboBox1.getSelectedItem(), "Smazání",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, // no custom icon
                        options, // button titles
                        options[0] // default button
                );
                try {
                    Polozky polozky = (Polozky) Naming.lookup("rmi://pokladna:12345/polozky");
                    if (pridavek) {

                        if (result == JOptionPane.YES_OPTION) {
                            polozky.deletePridavek(Integer.parseInt(id));
                            dispose();
                        }

                    } else {
                        if (result == JOptionPane.YES_OPTION) {
                            polozky.deletePolozka(Integer.parseInt(id));
                            dispose();
                        }
                    }

                } catch (MalformedURLException | RemoteException | NotBoundException e) {
                    e.printStackTrace();
                }

            }
        });

        setLocationRelativeTo(null);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 58,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(okButton, javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox1, 0, 91, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup().addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(okButton)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();
    }
}
