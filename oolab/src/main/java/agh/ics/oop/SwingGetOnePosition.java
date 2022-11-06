package agh.ics.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingGetOnePosition {
    private Vector2d vector2d;

    public SwingGetOnePosition(int i) {
        JFrame f = new JFrame("Running animals");

        JDialog d = new JDialog(f, i + ". animal position", true);
        d.setLayout(new FlowLayout());
        d.add(new JLabel("Enter position of the " + i  + ". animal (x, y): "));
        JTextField x = new JTextField(3);
        JTextField y = new JTextField(3);


        JButton b = new JButton("OK");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int X = Integer.parseInt(x.getText());
                int Y = Integer.parseInt(y.getText());
                vector2d = new Vector2d(X, Y);

                d.setVisible(false);
            }
        });
        d.add(x);
        d.add(y);
        d.add(b);
        d.setSize(600, 200);
        d.setVisible(true);
    }

    public Vector2d getVector2d() { return vector2d; }
}
