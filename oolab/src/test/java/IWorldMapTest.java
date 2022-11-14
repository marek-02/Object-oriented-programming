import agh.ics.oop.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IWorldMapTest {
    @Test
    public void canMoveToGrassFieldTest() {
        GrassField g = new GrassField(5);
        assertTrue(g.canMoveTo(new Vector2d(-10, 314280923)));
        assertTrue(g.canMoveTo(new Vector2d(132120, -32376)));
        Animal a1 = new Animal(g, new Vector2d(2, 2));
        Animal a2 = new Animal(g, new Vector2d(-6, -13));
        g.place(a1);
        g.place(a2);
        assertFalse(g.canMoveTo(new Vector2d(2,2)));
        assertFalse(g.canMoveTo(new Vector2d(-6,-13)));
        assertTrue(g.canMoveTo(new Vector2d(6,13)));
        a1.move(MoveDirection.FORWARD);
        a1.move(MoveDirection.RIGHT);
        a1.move(MoveDirection.BACKWARD);
        a2.move(MoveDirection.BACKWARD);
        a2.move(MoveDirection.BACKWARD);
        a2.move(MoveDirection.LEFT);
        a2.move(MoveDirection.FORWARD);
        assertFalse(g.canMoveTo(new Vector2d(1,3)));
        assertFalse(g.canMoveTo(new Vector2d(-7,-15)));
        assertTrue(g.canMoveTo(new Vector2d(2,2)));
        assertTrue(g.canMoveTo(new Vector2d(-6,-13)));
    }
    @Test
    public void canMoveToRectangularMapTest() {
        RectangularMap r = new RectangularMap(10,8);
        assertFalse(r.canMoveTo(new Vector2d(-10, 314280923)));
        assertFalse(r.canMoveTo(new Vector2d(4, -1)));
        assertFalse(r.canMoveTo(new Vector2d(9, 5)));
        assertTrue(r.canMoveTo(new Vector2d(8, 10)));
        Animal a1 = new Animal(r, new Vector2d(2, 2));
        Animal a2 = new Animal(r, new Vector2d(6, 7));
        r.place(a1);
        r.place(a2);
        assertFalse(r.canMoveTo(new Vector2d(2,2)));
        assertFalse(r.canMoveTo(new Vector2d(6,7)));
        a1.move(MoveDirection.FORWARD);
        a1.move(MoveDirection.RIGHT);
        a1.move(MoveDirection.BACKWARD);
        assertFalse(r.canMoveTo(new Vector2d(1,3)));
        a1.move(MoveDirection.BACKWARD);
        a1.move(MoveDirection.BACKWARD);
        a1.move(MoveDirection.BACKWARD);
        assertFalse(r.canMoveTo(new Vector2d(0,3)));
        a2.move(MoveDirection.BACKWARD);
        a2.move(MoveDirection.BACKWARD);
        a2.move(MoveDirection.LEFT);
        a2.move(MoveDirection.FORWARD);
        assertFalse(r.canMoveTo(new Vector2d(5,5)));
        assertTrue(r.canMoveTo(new Vector2d(2,2)));
        assertTrue(r.canMoveTo(new Vector2d(6,7)));
    }

    @Test
    public void placeGrassFieldTest() {
        GrassField g = new GrassField(8);
        Animal a1 = new Animal(g, new Vector2d(2, 2));
        Animal a2 = new Animal(g, new Vector2d(-9, -13));
        Animal a3 = new Animal(g, new Vector2d(-8, 1692));
        Animal a4 = new Animal(g, new Vector2d(-9, -13));
        assertTrue(g.place(a1));
        assertTrue(g.place(a2));
        assertTrue(g.place(a3));
        assertFalse(g.place(a4));
    }

    @Test
    public void placeRectangularMapTest() {
        RectangularMap r = new RectangularMap(6, 23);
        Animal a1 = new Animal(r, new Vector2d(2, 2));
        Animal a2 = new Animal(r, new Vector2d(1, -3));
        Animal a3 = new Animal(r, new Vector2d(-1, 2));
        Animal a4 = new Animal(r, new Vector2d(-9, -13));
        Animal a5 = new Animal(r, new Vector2d(6, 23));
        Animal a6 = new Animal(r, new Vector2d(4, 19));
        Animal a7 = new Animal(r, new Vector2d(2, 2));
        assertTrue(r.place(a1));
        assertTrue(r.place(a2));
        assertTrue(r.place(a3));
        assertTrue(r.place(a4));
        assertTrue(r.place(a5));
        assertTrue(r.place(a6));
        assertFalse(r.place(a7));
    }

    @Test
    public void isOccupiedGrassFieldTest() {
        GrassField g = new GrassField(8);
        Animal a1 = new Animal(g, new Vector2d(2, 2));
        Animal a2 = new Animal(g, new Vector2d(-9, -13));
        g.place(a1);
        g.place(a2);
        assertTrue(g.isOccupied(new Vector2d(2, 2)));
        assertTrue(g.isOccupied(new Vector2d(-9, -13)));
        assertFalse(g.isOccupied(new Vector2d(9, 13)) && !((g.objectAt((new Vector2d(9, 13)))) instanceof Grass) );
        assertFalse(g.isOccupied(new Vector2d(7, 286)) && !((g.objectAt((new Vector2d(7, 286)))) instanceof Grass) );
    }

    @Test
    public void isOccupiedRectangularMapTest() {
        RectangularMap r = new RectangularMap(13, 3);
        Animal a1 = new Animal(r, new Vector2d(3, 2));
        Animal a2 = new Animal(r, new Vector2d(0, 11));
        r.place(a1);
        r.place(a2);
        assertTrue(r.isOccupied(new Vector2d(3, 2)));
        assertTrue(r.isOccupied(new Vector2d(0, 11)));
        assertFalse(r.isOccupied(new Vector2d(2, 3)));
        assertFalse(r.isOccupied(new Vector2d(1, 1)));
    }

    @Test
    public void objectAtGrassFieldTest() {
        GrassField g = new GrassField(16);
        Animal a1 = new Animal(g, new Vector2d(8, 273));
        Animal a2 = new Animal(g, new Vector2d(313, -71));
        g.place(a1);
        g.place(a2);
        assertTrue(g.objectAt(new Vector2d(8, 273)) instanceof Animal);
        assertTrue(g.objectAt(new Vector2d(313, -71)) instanceof Animal);
        assertFalse(g.objectAt(new Vector2d(-8, -273)) instanceof Object);
        assertFalse(g.objectAt(new Vector2d(13, 6)) instanceof Object);
        assertFalse(g.objectAt(new Vector2d(11, 10)) instanceof Animal);
    }

    @Test
    public void objectAtRectangularMapTest() {
        RectangularMap r = new RectangularMap(27, 314);
        Animal a1 = new Animal(r, new Vector2d(286, 23));
        Animal a2 = new Animal(r, new Vector2d(0, 7));
        r.place(a1);
        r.place(a2);
        assertTrue(r.objectAt(new Vector2d(286, 23)) instanceof Animal);
        assertTrue(r.objectAt(new Vector2d(0, 7)) instanceof Animal);
        assertFalse(r.objectAt(new Vector2d(-3, 6)) instanceof Object);
        assertFalse(r.objectAt(new Vector2d(7, 0)) instanceof Object);
    }
}
