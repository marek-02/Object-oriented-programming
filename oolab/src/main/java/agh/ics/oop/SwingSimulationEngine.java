package agh.ics.oop;

import javax.swing.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.List;

public class SwingSimulationEngine implements ActionListener{
    private MoveDirection[] moves;
    private IWorldMap map;
    private List<Animal> animals;
    private JFrame f;
    private JTextArea area;
    private JButton b;
    private int i;
    private Timer timer;

    public SwingSimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] vectors){
        this.f = new JFrame("Animation");
        this.area = new JTextArea();
        this.b = new JButton("Start animation");
        this.moves = moves;
        this.map = map;
        this.animals = new ArrayList<>();
        this.i = 0;
        this.timer = null;

        b.setBounds(20, 20, 150, 50);
        area.setBounds(20, 100, 250, 500);

        area.setText(map.toString());

        for(Vector2d v : vectors){
            Animal a = new Animal(map, v);
            if(!map.isOccupied(v)) animals.add(a);
            map.place(a);
            //area.setText(map.toString());
        }
        b.addActionListener(this);
        f.add(area);
        f.add(b);
        f.setSize(700, 700);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    animals.get(i % animals.size()).move(moves[i]);
                    area.setText(map.toString());
                    i++;
                    //if (i < moves.length) { timer.stop(); }
                } catch (IndexOutOfBoundsException exception) {
                    timer.stop();
                    b.setText("Click to close");
                    b.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0);
                        }
                    });
                }
            }
        });
        timer.setRepeats(true);
        timer.start();
    }
}
