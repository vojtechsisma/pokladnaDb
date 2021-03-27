import javax.swing.JFrame;
import java.awt.Color;


public class PridavkyFrame extends JFrame{
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JButton jButton20;
        private javax.swing.JButton jButton21;
        //public Objednavka obj = Objednavka.getInstance();

        PridavkyFrame(Objednavka obj) {
            initPridavky(obj);
        }

        int slanina = 0;
        int syr = 0;
        private Objednavka initPridavky(Objednavka obj) {

                
                jButton1 = new javax.swing.JButton();
                jButton2 = new javax.swing.JButton();
                jButton20 = new javax.swing.JButton();
                jButton21 = new javax.swing.JButton();

                setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
                setResizable(false);

                jButton1.setText("slanina");
                jButton1.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                slanina++;
                                jButton1.setBackground(Color.green);

                        }
                });

                jButton2.setText("syr");
                jButton2.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                syr++;
                                jButton2.setBackground(Color.green);
                        }
                });

                jButton20.setText("");

                jButton21.setText("ok");

                jButton21.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                Iface chesseburger;
                                if (syr > 0 && slanina > 0) {
                                        chesseburger = new SyrDecorator(new SlaninaDecorator(new Chesseburger()));
                                        System.err.println("jifoa");
                                        ObjednavkaFrame.zapis(chesseburger.getNazev(), chesseburger.getCena());
                                        syr = 0;

                                } else if (slanina > 0) {
                                        chesseburger = new SlaninaDecorator(new Chesseburger());
                                        ObjednavkaFrame.zapis(chesseburger.getNazev(), chesseburger.getCena());
                                        slanina = 0;
                                } else if (syr > 0) {
                                        chesseburger = new SyrDecorator(new Chesseburger());
                                        ObjednavkaFrame.zapis(chesseburger.getNazev(), chesseburger.getCena());
                                        syr = 0;
                                } else {
                                        chesseburger = new Chesseburger();
                                        ObjednavkaFrame.zapis(chesseburger.getNazev(), chesseburger.getCena());

                                }
                                obj.pridej(chesseburger);

                                obj.vypis();
                                dispose();
                        }
                        
                });

                javax.swing.GroupLayout layout2 = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout2);
                layout2.setHorizontalGroup(layout2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout2.createSequentialGroup().addContainerGap().addGroup(layout2
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 173,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 173,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 173,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 173,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)));
                layout2.setVerticalGroup(layout2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout2.createSequentialGroup().addGap(12, 12, 12)
                                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 51,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)));

                pack();
                return(obj);
        }// </editor-fold>
}
