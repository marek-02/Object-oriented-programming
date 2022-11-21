package agh.ics.oop;

import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap{
    protected List<Animal> animals;

    abstract Vector2d getUpperRightCorner();
    abstract Vector2d getLowerLeftCorner();

    public boolean canMoveTo(Vector2d position) {
        for(Animal a : animals)
            if (a.getPosition().equals(position)) return false;
        return true;
    }
    public boolean place(Animal animal){
        if(canMoveTo(animal.getPosition())){
            animals.add(animal);
            return true;
        }
        return false;
    }
    public boolean isOccupied(Vector2d position) {
        for(Animal a : animals)
            if (a.getPosition().equals(position)) return true;
        return false;
    }
    public Object objectAt(Vector2d position) {
        for (Animal a : animals)
            if (a.getPosition().equals(position)) return a;
        return null;
    }

    public String toString(){
        MapVisualizer map = new MapVisualizer((IWorldMap) this);
        return map.draw(getLowerLeftCorner(), getUpperRightCorner());
    }
}
