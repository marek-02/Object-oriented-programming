package agh.ics.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingGrassCounter {
    private int grassCounter;
    public SwingGrassCounter() {
        JFrame f = new JFrame("Running animals");

        JDialog d = new JDialog(f, "Amount of Grass", true);
        //JDialog d = new JDialog();
        d.setLayout(new FlowLayout());
        d.add(new JLabel("Enter the amount of the Grass: "));
        JTextField x = new JTextField(3);


        JButton b = new JButton("OK");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String X = x.getText().trim();
                grassCounter = Integer.parseInt(X);

                d.setVisible(false);
            }
        });
        d.add(x);
        d.add(b);
        d.setSize(600, 200);
        d.setVisible(true);
    }

    public int getGrassCounter() {
        return grassCounter;
    }
}
