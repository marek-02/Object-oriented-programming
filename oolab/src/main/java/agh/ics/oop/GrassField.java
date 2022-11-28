package agh.ics.oop;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    private int amountOfGrass;
    private Map<Vector2d, Grass> grass;
    private MapBoundary observer;

    public GrassField(int amountOfGrass) {
        this.amountOfGrass = 0;
        this.grass = new HashMap<>();
        this.observer = new MapBoundary();

        int max = (int) Math.sqrt(amountOfGrass * 10);
        Random generator = new Random();

        while (this.amountOfGrass < amountOfGrass) {
            int x = generator.nextInt(max);
            int y = generator.nextInt(max);
            Vector2d v = new Vector2d(x, y);

            if(grass.get(v) == null) {
                grass.put(v, new Grass(v));
                observer.addElement(v);
                this.amountOfGrass++;
            }
        }
    }

    private boolean deleteGrass(Vector2d position) {
        if(grass.get(position) == null) return false;
        grass.remove(position);
        return true;
    }

    private void addNewGrass() {
        int max = (int) Math.sqrt(amountOfGrass * 10);
        Random generator = new Random();

        while (true) {
            int x = generator.nextInt(max);
            int y = generator.nextInt(max);
            Vector2d v = new Vector2d(x, y);

            if(objectAt(v) == null) {
                grass.put(v, new Grass(v));
                observer.addElement(v);
                return;
            }
        }
    }
    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        if (super.place(animal)) {
            observer.addElement(animal.getPosition());
            if(deleteGrass(animal.getPosition())) addNewGrass();
            return true;
        }
        throw new IllegalArgumentException(animal.getPosition() + " is a place where you cannot place element");
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        if (super.isOccupied(position)) return true;
        return grass.get(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object a = super.objectAt(position);
        if (a != null) return a;
        return grass.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        super.positionChanged(oldPosition, newPosition);
        if(deleteGrass(newPosition)) addNewGrass();
        this.observer.positionChanged(oldPosition, newPosition);
    }

    public MapBoundary getObserver() {
        return observer;
    }

    public Vector2d getUpperRightCorner() { return observer.getUpperRightCorner(); }
    public Vector2d getLowerLeftCorner() { return observer.getLowerLeftCorner(); }
}