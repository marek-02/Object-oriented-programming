package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    Vector2d vector1 = new Vector2d(0, 0);
    Vector2d vector2 = new Vector2d(6, 7);
    Vector2d vector3 = new Vector2d(-2, 3);
    Vector2d vector4 = new Vector2d(6, 7);
    Vector2d vector5 = new Vector2d(2, -3);
    Vector2d vector6 = new Vector2d(-1, -1);
    Vector2d vector7 = new Vector2d(0, 0);

    @Test
    void testEquals(){
        assertEquals(vector2, vector4);
        assertEquals(vector1, vector7);
        assertNotEquals(vector1, vector2);
        assertNotEquals(vector3, vector5);
        assertNotEquals(vector1, vector6);
        assertNotEquals(vector4, vector3);
    }
    @Test
    void testToString(){
        assertEquals(vector1.toString(), "(0,0)");
        assertEquals(vector2.toString(), "(6,7)");
        assertEquals(vector3.toString(), "(-2,3)");
        assertEquals(vector6.toString(), "(-1,-1)");
        assertNotEquals(vector4.toString(), "(7,6");
        assertNotEquals(vector5.toString(), "(0,0");
        assertNotEquals(vector7.toString(), "(-1,-1");
    }
    @Test
    void testPrecedes(){
        assertTrue(vector1.precedes(vector2));
        assertTrue(vector3.precedes(vector4));
        assertTrue(vector6.precedes(vector7));
        assertTrue(vector1.precedes(vector7));
        assertFalse(vector3.precedes(vector6));
        assertFalse(vector5.precedes(vector1));
        assertFalse(vector5.precedes(vector3));
        assertFalse(vector7.precedes(vector5));
    }
    @Test
    void testFollows(){
        assertTrue(vector2.follows(vector1));
        assertTrue(vector4.follows(vector3));
        assertTrue(vector7.follows(vector6));
        assertTrue(vector1.follows(vector7));
        assertFalse(vector6.follows(vector3));
        assertFalse(vector1.follows(vector5));
        assertFalse(vector3.follows(vector5));
        assertFalse(vector7.follows(vector4));
    }
    @Test
    void testUpperRight(){
        assertEquals(vector1.upperRight(vector2), vector2);
        assertEquals(vector7.upperRight(vector5), new Vector2d(2, 0));
        assertEquals(vector3.upperRight(vector6), new Vector2d(-1, 3));
        assertEquals(vector2.upperRight(vector4), vector4);
        assertNotEquals(vector6.upperRight(vector2), new Vector2d(-1, 7));
        assertNotEquals(vector5.upperRight(vector3), new Vector2d(-2, -3));
        assertNotEquals(vector1.upperRight(vector4), new Vector2d(0, 3));
    }
    @Test
    void testLowerLeft(){
        assertEquals(vector4.lowerLeft(vector7), vector7);
        assertEquals(vector1.lowerLeft(vector5), new Vector2d(0, -3));
        assertEquals(vector3.lowerLeft(vector6), new Vector2d(-2, -1));
        assertEquals(vector5.lowerLeft(vector3), new Vector2d(-2, -3));
        assertNotEquals(vector6.lowerLeft(vector2), vector2);
        assertNotEquals(vector3.lowerLeft(vector5), new Vector2d(-3, -2));
        assertNotEquals(vector7.lowerLeft(vector2), new Vector2d(0, 3));
    }
    @Test
    void testAdd(){
        assertEquals(vector2.add(vector1), vector2);
        assertEquals(vector3.add(vector5), vector1);
        assertEquals(vector6.add(vector3), new Vector2d(-3, 2));
        assertEquals(vector2.add(vector4), new Vector2d(12, 14));
        assertNotEquals(vector6.add(vector2), new Vector2d(-1, 3));
        assertNotEquals(vector5.add(vector3), new Vector2d(-4, -6));
        assertNotEquals(vector1.add(vector4), new Vector2d(7, 6));
    }
    @Test
    void testSubtract(){
        assertEquals(vector2.subtract(vector1), vector2);
        assertEquals(vector3.subtract(vector5), new Vector2d(-4, 6));
        assertEquals(vector6.subtract(vector3), new Vector2d(1, -4));
        assertEquals(vector2.subtract(vector4), vector1);
        assertNotEquals(vector6.subtract(vector2), new Vector2d(-8, -7));
        assertNotEquals(vector5.subtract(vector3), new Vector2d(-4, 6));
        assertNotEquals(vector1.subtract(vector4), new Vector2d(12, -4));
    }
    @Test
    void testOpposite(){
        assertEquals(vector1.opposite(), vector1);
        assertEquals(vector2.opposite(), new Vector2d(-6, -7));
        assertEquals(vector3.opposite(), new Vector2d(2, -3));
        assertEquals(vector6.opposite(), new Vector2d(1, 1));
        assertNotEquals(vector4.opposite(), new Vector2d(-7, -6));
        assertNotEquals(vector5.opposite(), new Vector2d(3, -2));
        assertNotEquals(vector7.opposite(), new Vector2d(-2, 16));
        assertNotEquals(vector6.opposite(), new Vector2d(-1, -1));
    }
}
