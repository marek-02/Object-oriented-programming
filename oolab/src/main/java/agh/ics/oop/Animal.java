package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class Animal {
    private MapDirection direction;
    private Vector2d position;
    private IWorldMap map;
    private List<IPositionChangeObserver> observers;
    public Animal(){
        this(new RectangularMap(4, 4));
    }
    public Animal(IWorldMap map){
        this(map, new Vector2d(2, 2));
    }
    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.position = initialPosition;
        this.direction = MapDirection.NORTH;
        this.observers = new ArrayList<>();
    }

    public MapDirection getDirection() {
        return direction;
    }
    public IWorldMap getMap() {
        return map;
    }
    public Vector2d getPosition() {
        return position;
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public void addObserver( IPositionChangeObserver obs){ observers.add(obs); }
    public void removeObserver( IPositionChangeObserver obs){ observers.remove(obs); }

    private void positionChanged(Vector2d older, Vector2d newer){
        for(IPositionChangeObserver obs : observers) obs.positionChanged(older, newer);
    }

    @Override
    public String toString(){
        return switch (this.direction){
            case NORTH -> "^"; //"N"
            case EAST -> ">"; //"E"
            case SOUTH -> "v"; //"S"
            case WEST -> "<"; //"W"
        };
    }
    public void move(MoveDirection direction){
        Vector2d older = this.position;
        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> {
                Vector2d added = this.position.add(this.direction.toUnitVector());
                if(map.canMoveTo(added)) {
                    this.position = added;
                }
            }
            case BACKWARD -> {
                Vector2d subtracted = this.position.subtract(this.direction.toUnitVector());
                if(map.canMoveTo(subtracted)) {
                    this.position = subtracted;
                }
            }
        }
        positionChanged(older, this.position);
    }
}
