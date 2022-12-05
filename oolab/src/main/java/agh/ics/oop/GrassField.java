package agh.ics.oop;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    private int amountOfGrass;
    private Map<Vector2d, Grass> grass;
    private List<IGrassFieldObserver> observers;

    public GrassField(int amountOfGrass) {
        this.amountOfGrass = 0;
        this.grass = new HashMap<>();
        this.observers = new ArrayList<>();
        addObserver(new MapBoundary());

        int max = (int) Math.sqrt(amountOfGrass * 10);
        Random generator = new Random();

        while (this.amountOfGrass < amountOfGrass) {
            int x = generator.nextInt(max);
            int y = generator.nextInt(max);
            Vector2d v = new Vector2d(x, y);

            if(grass.get(v) == null) {
                grass.put(v, new Grass(v));
                for (IGrassFieldObserver observer : observers) observer.addElement(v);
                this.amountOfGrass++;
            }
        }
    }

    public void addObserver( IGrassFieldObserver obs){ observers.add(obs); }
    public void removeObserver( IGrassFieldObserver obs){ if(observers.size() > 1) observers.remove(obs); }

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
                for (IGrassFieldObserver observer : observers) observer.addElement(v);
                return;
            }
        }
    }
    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        if (super.place(animal)) {
            if(deleteGrass(animal.getPosition())) addNewGrass();
            for (IGrassFieldObserver observer : observers) observer.addElement(animal.getPosition());
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
        for (IGrassFieldObserver observer : observers) observer.positionChanged(oldPosition, newPosition);
    }

    @Override
    public Vector2d[] getMapElementPositions() {
        Vector2d[] animalsPositions = super.getMapElementPositions();
        HashSet<Vector2d> elementsPositions = new HashSet<>();
        elementsPositions.addAll(Arrays.asList(animalsPositions));
        elementsPositions.addAll(grass.keySet());
        return elementsPositions.toArray(new Vector2d[0]);
    }

    public Vector2d getUpperRightCorner() { return observers.get(0).getUpperRightCorner(); }
    public Vector2d getLowerLeftCorner() { return observers.get(0).getLowerLeftCorner(); }
}