package agh.ics.oop;

public class Animal {
    private MapDirection direction = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2, 2);

    @Override
    public String toString(){
        return "Położenie: " + position.toString() + ", orientacja: " + direction.toString();
    }

    public boolean isAt(Vector2d position){
        return this.position.equals(position);
    }

    public void move(MoveDirection direction){
        switch (direction) {
            case RIGHT -> this.direction = this.direction.next();
            case LEFT -> this.direction = this.direction.previous();
            case FORWARD -> {
                Vector2d uR = new Vector2d(4, 4);
                Vector2d lL = new Vector2d(0, 0);
                Vector2d added = this.position.add(this.direction.toUnitVector());
                this.position = uR.lowerLeft(lL.upperRight(added));
            }
            case BACKWARD -> {
                Vector2d uR = new Vector2d(4, 4);
                Vector2d lL = new Vector2d(0, 0);
                Vector2d subtracted = this.position.subtract(this.direction.toUnitVector());
                this.position = uR.lowerLeft(lL.upperRight(subtracted));
            }
        }
    }
}
