package agh.ics.oop;

import java.util.Comparator;

public class CompareVectorX implements Comparator<Vector2d> {
    @Override
    public int compare(Vector2d a, Vector2d b) {
        return a.getX() - b.getX() != 0 ? a.getX() - b.getX() : a.getY() - b.getY();
    }
}
