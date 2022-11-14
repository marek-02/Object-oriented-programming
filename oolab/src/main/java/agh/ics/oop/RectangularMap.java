package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap extends AbstractWorldMap{
    private final int height;
    private final int width;
    private Vector2d upperRightCorner;
    private Vector2d lowerLeftCorner;

    public RectangularMap(int h, int w){
        this.height = h;
        this.width = w;
        this.upperRightCorner = new Vector2d(width, height);
        this.lowerLeftCorner = new Vector2d(0, 0);
        this.animals = new ArrayList<>();
    }

    public boolean canMoveTo(Vector2d position){
        return position.follows(lowerLeftCorner) && position.precedes(upperRightCorner) && !isOccupied(position);
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

    public Vector2d getUpperRightCorner() { return upperRightCorner; }
    public Vector2d getLowerLeftCorner() { return lowerLeftCorner; }
}
