package agh.ics.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingCommands {
    private String[] commands;

    public SwingCommands() {
        JFrame f = new JFrame("Running animals");

        JDialog d = new JDialog(f, "Commands for animals", true);
        d.setLayout(new FlowLayout());
        d.add(new JLabel("Enter commands for the animals (command1 command2 command3 ...): "));
        JTextField args = new JTextField(10);

        JButton b = new JButton("OK");
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands = args.getText().split(" ");

                d.setVisible(false);
            }
        });
        d.add(args);
        d.add(b);
        d.setSize(600, 200);
        d.setVisible(true);
    }

    public String[] getCommands(){ return commands; }
}
