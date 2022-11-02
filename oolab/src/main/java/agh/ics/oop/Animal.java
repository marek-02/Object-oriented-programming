package agh.ics.oop;

public class Animal {
    private MapDirection direction;
    private Vector2d position;
    private IWorldMap map;

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
        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> {
                Vector2d added = this.position.add(this.direction.toUnitVector());
                if(map.canMoveTo(added)) this.position = added;
            }
            case BACKWARD -> {
                Vector2d subtracted = this.position.subtract(this.direction.toUnitVector());
                if(map.canMoveTo(subtracted)) this.position = subtracted;
            }
        }
    }
}
