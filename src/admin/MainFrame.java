package admin;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Admin");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(2, 1, 20, 20));
        frame.setResizable(false);

        JButton b = new JButton("Nová položka");
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PolozkaFrame pframe = new PolozkaFrame(0);
                pframe.setVisible(true);

            }
        });
        frame.add(b);

        JButton up = new JButton("Upravit položku");
        up.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditFrame ed = new EditFrame(false);
                ed.setVisible(true);

            }
        });
        frame.add(up);

        JButton sp = new JButton("Smazat položku");
        sp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeleteFrame df = new DeleteFrame(false);
                df.setVisible(true);

            }
        });
        frame.add(sp);

        JButton novyPridavek = new JButton("Nový přídavek");
        novyPridavek.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PridavekFrame prframe = new PridavekFrame(0);
                prframe.setVisible(true);

            }
        });
        frame.add(novyPridavek);

        JButton spr = new JButton("Smazat přídavek");
        spr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeleteFrame dfp = new DeleteFrame(true);
                dfp.setVisible(true);

            }
        });
        frame.add(spr);

        JButton upr = new JButton("Upravit přídavek");
        upr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditFrame edf = new EditFrame(true);
                edf.setVisible(true);

            }
        });
        frame.add(upr);

        JButton vypsat = new JButton("Vypsat objednávku");
        vypsat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VypisFrame v = new VypisFrame();
                v.setVisible(true);
            }
        });
        frame.add(vypsat);

        JButton end = new JButton("Konec");
        end.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(0);
            }
        });
        end.setBackground(new Color(228, 2, 27));

        frame.add(end);

        frame.setSize(960, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
