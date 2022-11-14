package agh.ics.oop;

public class Grass {
    private Vector2d pos;

    public  Grass() { this( new Vector2d(0, 0)); }
    public Grass(Vector2d v)
    {
        pos = v;
    }

    public Vector2d getPosition() {
        return pos;
    }

    @Override
    public String toString() {
        return "*";
    }
}
