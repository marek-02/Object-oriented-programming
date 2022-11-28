package agh.ics.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class SwingSimulationEngine implements ActionListener{
    private MoveDirection[] moves;
    private AbstractWorldMap map;
    private List<Animal> animals;
    private JFrame f;
    private JTextArea area;
    private JButton b;
    private int i;
    private Timer timer;

    public SwingSimulationEngine(MoveDirection[] moves, AbstractWorldMap map, Vector2d[] vectors){
        this.f = new JFrame("Animation");
        this.area = new JTextArea();
        this.b = new JButton("Start animation");
        this.moves = moves;
        this.map = map;
        this.animals = new ArrayList<>();
        this.i = 0;
        this.timer = null;

        for(Vector2d v : vectors){
            Animal a = new Animal(map, v);
            if(map.place(a)) animals.add(a);
        }

        Vector2d ur = map.getUpperRightCorner();
        Vector2d ll = map.getLowerLeftCorner();
        int w = ur.getX() - ll.getX();
        int h = ur.getY() - ll.getY();
//        System.out.println(ur);
//        System.out.println(ll);
//        System.out.println(w);
//        System.out.println(h);
        int x = max(190, 20 * (w + 5));
        int y = max(20, 20 * (h + 4));

        if (map instanceof GrassField) {
            int worstPossibility = moves.length / animals.size();
            x = max(x, 20 * (w + worstPossibility + 2));
            y = max(y, 20 * (h + worstPossibility + 2));
        }


        b.setBounds(20, 25, 150, 50);
        area.setBounds(20, 100, x, y);

        area.setText(map.toString());

        area.setText(map.toString());
        b.addActionListener(this);
        f.add(area);
        f.add(b);
        f.setSize(x + 60, y + 160);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        area.setFont(new Font("Courier New", Font.PLAIN, 16));
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
