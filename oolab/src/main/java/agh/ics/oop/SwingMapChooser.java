package agh.ics.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingMapChooser {
    private String decision;
    public SwingMapChooser() {
        JFrame f = new JFrame("Running animals");

        JDialog d = new JDialog(f, "Map type", true);
        //JDialog d = new JDialog();
        d.setLayout(new FlowLayout());
        d.add(new JLabel("Do you want grass on the map (if yes, type yes or y): "));
        JTextField x = new JTextField(6);


        JButton b = new JButton("OK");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decision = x.getText().trim();
                //d.dispose();
                d.setVisible(false);
            }
        });
        d.add(x);
        d.add(b);
        d.setSize(600, 200);
        d.setVisible(true);
    }

    public String getDecision() {
        return decision;
    }
}
