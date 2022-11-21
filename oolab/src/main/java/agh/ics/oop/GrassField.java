package agh.ics.oop;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    private int amountOfGrass;
    private Map<Vector2d, Grass> grass;
    private Vector2d upperRightCorner;
    private Vector2d lowerLeftCorner;

    public GrassField(int amountOfGrass) {
        this.amountOfGrass = 0;
        grass = new HashMap<>();
        animals = new HashMap<>();
        this.upperRightCorner = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.lowerLeftCorner = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);

        int max = (int) Math.sqrt(amountOfGrass * 10);
        Random generator = new Random();

        while (this.amountOfGrass < amountOfGrass) {
            int x = generator.nextInt(max);
            int y = generator.nextInt(max);
            Vector2d v = new Vector2d(x, y);

            if(grass.get(v) == null) {
                grass.put(v, new Grass(v));
                this.amountOfGrass++;
                upperRightCorner = v.upperRight(upperRightCorner);
                lowerLeftCorner = v.lowerLeft(lowerLeftCorner);
            }
        }
    }

    public boolean deleteGrass(Vector2d position) {
        if(grass.get(position) == null) return false;
        grass.remove(position);
        return true;
    }

    public void addNewGrass() {
        int max = (int) Math.sqrt(amountOfGrass * 10);
        Random generator = new Random();

        while (true) {
            int x = generator.nextInt(max);
            int y = generator.nextInt(max);
            Vector2d v = new Vector2d(x, y);

            if(objectAt(v) == null) {
                grass.put(v, new Grass(v));
                upperRightCorner = v.upperRight(upperRightCorner);
                lowerLeftCorner = v.lowerLeft(lowerLeftCorner);
                return;
            }
        }
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        if (super.canMoveTo(position)) {
            upperRightCorner = position.upperRight(upperRightCorner);
            lowerLeftCorner = position.lowerLeft(lowerLeftCorner);
            return true;
        }
        return false;
    }

    @Override
    public boolean place(Animal animal){
        if (super.place(animal)) {
            if(deleteGrass(animal.getPosition())) addNewGrass();
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if (super.isOccupied(position)) return true;
        if (grass.get(position) != null) return true;
        upperRightCorner = position.upperRight(upperRightCorner);
        lowerLeftCorner = position.lowerLeft(lowerLeftCorner);
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object a = super.objectAt(position);
        if (a != null) return a;
        return grass.get(position);
    }

    public Vector2d getUpperRightCorner() { return upperRightCorner; }
    public Vector2d getLowerLeftCorner() { return lowerLeftCorner; }
}