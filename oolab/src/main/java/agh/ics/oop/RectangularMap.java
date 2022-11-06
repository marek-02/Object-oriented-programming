package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap implements IWorldMap{
    private final int height;
    private final int width;
    private final Vector2d upperRightCorner;
    private final Vector2d lowerLeftCorner;
    private List<Animal> animals;
    private final MapVisualizer visualizer;

    public RectangularMap(int h, int w){
        this.height = h;
        this.width = w;
        this.upperRightCorner = new Vector2d(width, height);
        this.lowerLeftCorner = new Vector2d(0, 0);
        this.animals = new ArrayList<>();
        this.visualizer = new MapVisualizer(this);
    }

    public boolean canMoveTo(Vector2d position){
        return position.follows(lowerLeftCorner) && position.precedes(upperRightCorner) && !isOccupied(position);
    }
    public boolean place(Animal animal){
        if(!isOccupied(animal.getPosition())){
            animals.add(animal);
            return true;
        }
        return false;
    }
    public boolean isOccupied(Vector2d position){
        for(Animal a : animals)
            if (a.getPosition().equals(position)) return true;
        return false;
    }
    public Object objectAt(Vector2d position){
        for(Animal a : animals)
            if (a.getPosition().equals(position)) return a;
        return null;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public String toString(){
        return this.visualizer.draw(lowerLeftCorner, upperRightCorner);
    }
}
