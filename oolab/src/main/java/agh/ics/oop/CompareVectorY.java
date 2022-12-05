package agh.ics.oop;

import java.util.Comparator;

public class CompareVectorY implements Comparator<Vector2d> {
    @Override
    public int compare(Vector2d a, Vector2d b) {
        return a.getY() - b.getY() != 0 ? a.getY() - b.getY() : a.getX() - b.getX();
    }
}
