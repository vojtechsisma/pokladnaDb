package pokladna;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Objednávka");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 1, 20, 20));
        frame.setResizable(false);

        JButton b = new JButton("Nová objednávka");
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ObjednavkaFrame oframe = new ObjednavkaFrame();
                oframe.setVisible(true);

            }
        });
        frame.add(b);

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

        frame.setSize(560, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
