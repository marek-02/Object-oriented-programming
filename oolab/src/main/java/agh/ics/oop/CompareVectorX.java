package agh.ics.oop;

import java.util.Comparator;

public class CompareVectorX implements Comparator<Vector2d> {
    @Override
    public int compare(Vector2d a, Vector2d b) {
        return a.x - b.x != 0 ? a.x - b.x : a.y - b.y;
    }
}
