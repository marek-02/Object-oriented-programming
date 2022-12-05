package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.Grass;
import agh.ics.oop.MapDirection;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    public Image getImageOfElement(Object o) {
        Image image = null;
        if (o instanceof Grass) {
            try {
                image = new Image(new FileInputStream("src/main/resources/grass.png"));
            } catch (FileNotFoundException ex) {
                System.out.println("Grass doesn't have image representation.");
            }
        } else if (o instanceof Animal) {
            MapDirection d = ((Animal) o).getDirection();
            switch (d) {
                case NORTH -> {
                    try {
                        image = new Image(new FileInputStream("src/main/resources/animal-up.png"));
                    } catch (FileNotFoundException ex) {
                        System.out.println("Animal turned upside doesn't have image representation.");
                    }
                }
                case SOUTH -> {
                    try {
                        image = new Image(new FileInputStream("src/main/resources/animal-down.png"));
                    } catch (FileNotFoundException ex) {
                        System.out.println("Animal turned to the bottom doesn't have image representation.");
                    }
                }
                case EAST -> {
                    try {
                        image = new Image(new FileInputStream("src/main/resources/animal-right.png"));
                    } catch (FileNotFoundException ex) {
                        System.out.println("Animal turned right doesn't have image representation.");
                    }
                }
                case WEST -> {
                    try {
                        image = new Image(new FileInputStream("src/main/resources/animal-left.png"));
                    } catch (FileNotFoundException ex) {
                        System.out.println("Animal turned left doesn't have image representation.");
                    }
                }
            }
        }
        return image;
    }
}
