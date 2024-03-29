package agh.ics.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingMapDimensions {
    private int xMap;
    private int yMap;
    public SwingMapDimensions() {
        JFrame f = new JFrame("Running animals");

        JDialog d = new JDialog(f, "Map dimensions", true);
        //JDialog d = new JDialog();
        d.setLayout(new FlowLayout());
        d.add(new JLabel("Enter the dimensions of the map (width, height): "));
        JTextField x = new JTextField(3);
        JTextField y = new JTextField(3);


        JButton b = new JButton("OK");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String X = x.getText().trim();
                String Y = y.getText().trim();
                xMap = Integer.parseInt(X) - 1;
                yMap = Integer.parseInt(Y) - 1;

                //d.dispose();
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
