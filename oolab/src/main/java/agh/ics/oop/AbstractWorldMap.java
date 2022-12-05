package agh.ics.oop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected Map<Vector2d, Animal> animals = new HashMap<>();;

    abstract public Vector2d getUpperRightCorner();
    abstract public Vector2d getLowerLeftCorner();

    public boolean canMoveTo(Vector2d position) {
        if(animals.get(position) == null) return true;
        return false;
    }
    public boolean place(Animal animal) throws IllegalArgumentException {
        if(canMoveTo(animal.getPosition())){
            animals.put(animal.getPosition(), animal);
            animal.addObserver(this);
            return true;
        }
        throw new IllegalArgumentException(animal.getPosition() + " is a place where you cannot place element");

    }
    public boolean isOccupied(Vector2d position) {
        if(animals.get(position) == null) return false;
        return true;
    }
    public Object objectAt(Vector2d position) {
        return animals.get(position);
    }

    public String toString(){
        MapVisualizer map = new MapVisualizer((IWorldMap) this);
        return map.draw(getLowerLeftCorner(), getUpperRightCorner());
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        Animal a = animals.remove(oldPosition);
        animals.put(newPosition, a);
    }

    public Vector2d[] getMapElementPositions() { return animals.keySet().toArray(new Vector2d[0]); }
}
