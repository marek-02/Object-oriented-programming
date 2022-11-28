package agh.ics.oop;

import java.util.Comparator;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver{
    private final TreeSet<Vector2d> xVectors = new TreeSet<>(new CompareVectorX()); //.thenComparingInt(p -> p.y));
    private final TreeSet<Vector2d> yVectors = new TreeSet<>(new CompareVectorY());

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        xVectors.remove(oldPosition);
        xVectors.add(newPosition);
        yVectors.remove(oldPosition);
        yVectors.add(newPosition);
    }

    public void addElement(Vector2d position) {
        xVectors.add(position);
        yVectors.add(position);
    }

    public void removeElement(Vector2d position) {
        xVectors.remove(position);
        yVectors.remove(position);
    }

    public Vector2d getUpperRightCorner() {
        return xVectors.last().upperRight(yVectors.last());
    }

    public Vector2d getLowerLeftCorner() {
        return xVectors.first().lowerLeft(yVectors.first());
    }

    public TreeSet<Vector2d> getxVectors() {
        return xVectors;
    }
}
