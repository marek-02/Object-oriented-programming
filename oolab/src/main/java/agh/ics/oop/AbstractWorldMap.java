package agh.ics.oop;

import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap{
    protected List<Animal> animals;

    abstract Vector2d getUpperRightCorner();
    abstract Vector2d getLowerLeftCorner();

    abstract public boolean canMoveTo(Vector2d position);
    public boolean place(Animal animal){
        if(!isOccupied(animal.getPosition())){
            animals.add(animal);
            return true;
        }
        return false;
    }
    abstract public boolean isOccupied(Vector2d position);
    abstract public Object objectAt(Vector2d position);

    public String toString(){
        MapVisualizer map = new MapVisualizer((IWorldMap) this);
        return map.draw(getLowerLeftCorner(), getUpperRightCorner());
    }
}
