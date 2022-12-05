package agh.ics.oop;

import java.util.Comparator;
import java.util.TreeSet;

public class MapBoundary implements IGrassFieldObserver {
    private final TreeSet<Vector2d> xVectors = new TreeSet<>(new CompareVectorX()); //.thenComparingInt(p -> p.y));
    private final TreeSet<Vector2d> yVectors = new TreeSet<>(new CompareVectorY());

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        xVectors.remove(oldPosition);
        xVectors.add(newPosition);
        yVectors.remove(oldPosition);
        yVectors.add(newPosition);
    }

    @Override
    public void addElement(Vector2d position) {
        xVectors.add(position);
        yVectors.add(position);
    }

    @Override
    public void removeElement(Vector2d position) {
        xVectors.remove(position);
        yVectors.remove(position);
    }

    @Override
    public Vector2d getUpperRightCorner() {
        return xVectors.last().upperRight(yVectors.last());
    }

    @Override
    public Vector2d getLowerLeftCorner() {
        return xVectors.first().lowerLeft(yVectors.first());
    }

}
