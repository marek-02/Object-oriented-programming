package agh.ics.oop;

public interface IGrassFieldObserver extends IPositionChangeObserver {
    void addElement(Vector2d position);
    void removeElement(Vector2d position);
    Vector2d getUpperRightCorner();

    Vector2d getLowerLeftCorner();
}
