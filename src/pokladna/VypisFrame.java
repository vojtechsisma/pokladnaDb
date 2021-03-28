package pokladna;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.print.PrinterException;


public class VypisFrame extends javax.swing.JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    VypisFrame() {
        initComponents();
    }

    public void initComponents() {
        JButton o1 = new javax.swing.JButton();
        o1.setText("vypsat objednávku č. 1");
        o1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vypis(0);
            }
        });
        JButton o2 = new javax.swing.JButton();
        o2.setText("vypsat objednávku č. 2");
        o2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vypis(1);
            }
        });
        JButton o3 = new javax.swing.JButton();
        o3.setText("vypsat objednávku č. 3");
        o3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vypis(2);
            }
        });
        JButton o4 = new javax.swing.JButton();
        o4.setText("vypsat objednávku č. 4");
        o4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vypis(3);
            }
        });
        JButton o5 = new javax.swing.JButton();
        o5.setText("vypsat objednávku č. 5");
        o5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vypis(4);
            }
        });
        JButton o6 = new javax.swing.JButton();
        o6.setText("vypsat objednávku č. 6");
        o6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vypis(5);
            }
        });
        JButton o7 = new javax.swing.JButton();
        o7.setText("vypsat objednávku č. 7");
        o7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vypis(6);
            }
        });
        JButton o8 = new javax.swing.JButton();
        o8.setText("vypsat objednávku č. 8");
        o8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vypis(7);
            }
        });
        JButton o9 = new javax.swing.JButton();
        o9.setText("vypsat objednávku č. 9");
        o9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vypis(8);
            }
        });
        JButton o10 = new javax.swing.JButton();
        o10.setText("vypsat objednávku č. 10");
        o10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vypis(9);
            }
        });
        setLocation(200, 200);
        setResizable(false);
        javax.swing.GroupLayout layout2 = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout2);
        layout2.setHorizontalGroup(layout2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout2.createSequentialGroup().addContainerGap()
                        .addGroup(layout2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(o1, javax.swing.GroupLayout.PREFERRED_SIZE, 173,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(o2, javax.swing.GroupLayout.PREFERRED_SIZE, 173,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(o3, javax.swing.GroupLayout.PREFERRED_SIZE, 173,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(o4, javax.swing.GroupLayout.PREFERRED_SIZE, 173,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(o5, javax.swing.GroupLayout.PREFERRED_SIZE, 173,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(o6, javax.swing.GroupLayout.PREFERRED_SIZE, 173,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(o7, javax.swing.GroupLayout.PREFERRED_SIZE, 173,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(o8, javax.swing.GroupLayout.PREFERRED_SIZE, 173,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(o9, javax.swing.GroupLayout.PREFERRED_SIZE, 173,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(o10, javax.swing.GroupLayout.PREFERRED_SIZE, 173,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        layout2.setVerticalGroup(layout2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout2
                .createSequentialGroup().addGap(12, 12, 12)
                .addComponent(o1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o7, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o8, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o9, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(o10, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

        pack();

    }

    public void vypis(int index) {
        Uloziste u = Uloziste.getInstance();
        Objednavka o = null;
        JTextArea area = new JTextArea();
        JTextArea area2 = new JTextArea();
        JFrame frame = null;
        try {

            frame = new JFrame();
            frame.setVisible(true);
            frame.setTitle("Objednávka: " + (index + 1));

            o = (Objednavka) u.getObjednavka(index);
            for (int i = 0; i < o.getPocet(); i++) {
                System.out.println(o.getObjednavka(i).getNazev());
                System.out.println(o.getObjednavka(i).getCena());
            }
            for (int i = 0; i < o.getPocet(); i++) {
                area.append(o.getObjednavka(i).getNazev());
                area.append("\n\t----\n\t" + Integer.toString(o.getObjednavka(i).getCena()) + "\n");
            }
            area2.append("Celkem: " + Integer.toString(o.getCelkovaCena()));
            area2.setEditable(false);
            area.setEditable(false);

            String[] options = { "ano", "ne" };
            int result = JOptionPane.showOptionDialog(frame, "Chcete objednávku vytisknout na fyzické tiskárně??", "Tisk",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, // no custom icon
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


            Printer p = Printer.getInstance();
            p.tiskni(index);
            JScrollPane jScrollPane1 = new JScrollPane();

            frame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            frame.setLocation(200, 200);

            area.setColumns(20);
            area.setRows(10);
            area2.setColumns(20);
            area2.setRows(5);
            jScrollPane1.setViewportView(area);

            /*
             * JScrollPane jScrollPane2 = new JScrollPane();
             * jScrollPane2.setViewportView(area2);
             */

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
