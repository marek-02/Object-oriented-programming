package agh.ics.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SwingStartPositions {
    private int numberOfAnimals;
    private List<Vector2d> positions;

    public SwingStartPositions(){
        JFrame f = new JFrame("Running animals");

        JDialog d = new JDialog(f, "The initial positions positions of the animals", true);
        d.setLayout(new FlowLayout());
        d.add(new JLabel("Enter the number of animals: "));
        JTextField number = new JTextField(3);

        JButton b = new JButton("OK");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numberOfAnimals = Integer.parseInt(number.getText().trim());
                positions = new ArrayList<>();
                for(int i = 0; i < numberOfAnimals; i++){
                    positions.add(new SwingGetOnePosition(i + 1).getVector2d());
                }
                d.setVisible(false);
            }
        });
        d.add(number);
        d.add(b);
        d.setSize(600, 200);
        d.setVisible(true);
    }

    public List<Vector2d> getPositions() { return positions; }
}
