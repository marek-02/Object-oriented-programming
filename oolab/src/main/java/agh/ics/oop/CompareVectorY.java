package agh.ics.oop;

import java.util.Comparator;

public class CompareVectorY implements Comparator<Vector2d> {
    @Override
    public int compare(Vector2d a, Vector2d b) {
        return a.y - b.y != 0 ? a.y - b.y : a.x - b.x;
    }
}
