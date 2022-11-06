package agh.ics.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class SwingMapDimensions {
    private int xMap;
    private int yMap;
    public SwingMapDimensions() {
        JFrame f = new JFrame("Running animals");

        JDialog d = new JDialog(f, "Map dimensions", true);
        d.setLayout(new FlowLayout());
        d.add(new JLabel("Enter the dimensions of the map (width, height): "));
        JTextField x = new JTextField(3);
        JTextField y = new JTextField(3);

        JButton b = new JButton("OK");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String X = x.getText();
                String Y = y.getText();
                xMap = Integer.parseInt(X);
                yMap = Integer.parseInt(Y);

                d.setVisible(false);
            }
        });
        d.add(x);
        d.add(y);
        d.add(b);
        d.setSize(600, 200);
        d.setVisible(true);
    }

    public int getxMap() { return xMap; }
    public int getyMap() { return yMap; }
}
