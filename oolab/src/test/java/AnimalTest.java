package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AnimalTest {

    @Test
    public void orientationTest(){
        Animal oA = new Animal();
        assertEquals(oA.toString(), "^");
        oA.move(MoveDirection.LEFT);
        assertEquals(oA.toString(), "<");
        oA.move(MoveDirection.RIGHT);
        oA.move(MoveDirection.RIGHT);
        assertEquals(oA.toString(), ">");
        oA.move(MoveDirection.RIGHT);
        assertNotEquals(oA.toString(), ">");
        oA.move(MoveDirection.LEFT);
        oA.move(MoveDirection.LEFT);
        oA.move(MoveDirection.LEFT);
        assertNotEquals(oA.toString(), "^");
        oA.move(MoveDirection.RIGHT);
        assertNotEquals(oA.toString(), "v");
    }

    @Test
    public void positionTest(){
        Animal pA = new Animal();
        pA.move(MoveDirection.FORWARD);
        assertTrue(pA.isAt(new Vector2d(2, 3)));
        pA.move(MoveDirection.LEFT);
        pA.move(MoveDirection.FORWARD);
        assertTrue(pA.isAt(new Vector2d(1, 3)));
        pA.move(MoveDirection.RIGHT);
        pA.move(MoveDirection.BACKWARD);
        assertTrue(pA.isAt(new Vector2d(1, 2)));
        pA.move(MoveDirection.FORWARD);
        assertFalse(pA.isAt(new Vector2d(1, 2)));
        pA.move(MoveDirection.RIGHT);
        pA.move(MoveDirection.FORWARD);
        pA.move(MoveDirection.FORWARD);
        assertFalse(pA.isAt(new Vector2d(1, 4)));
    }

    @Test
    public void mapTest(){
        Animal mA = new Animal();
        mA.move(MoveDirection.FORWARD);
        mA.move(MoveDirection.FORWARD);
        mA.move(MoveDirection.FORWARD);
        mA.move(MoveDirection.FORWARD);
        assertTrue(mA.getPosition().equals(new Vector2d(2, 4)));
        assertEquals(mA.toString(), "^");
        mA.move(MoveDirection.LEFT);
        mA.move(MoveDirection.FORWARD);
        mA.move(MoveDirection.FORWARD);
        mA.move(MoveDirection.FORWARD);
        assertTrue(mA.getPosition().equals(new Vector2d(0, 4)));
        assertEquals(mA.toString(), "<");
        mA.move(MoveDirection.BACKWARD);
        mA.move(MoveDirection.BACKWARD);
        mA.move(MoveDirection.BACKWARD);
        mA.move(MoveDirection.BACKWARD);
        mA.move(MoveDirection.BACKWARD);
        mA.move(MoveDirection.BACKWARD);
        assertTrue(mA.getPosition().equals(new Vector2d(4, 4)));
        assertEquals(mA.toString(), "<");
        mA.move(MoveDirection.LEFT);
        mA.move(MoveDirection.FORWARD);
        mA.move(MoveDirection.FORWARD);
        mA.move(MoveDirection.FORWARD);
        mA.move(MoveDirection.FORWARD);
        mA.move(MoveDirection.FORWARD);
        assertTrue(mA.getPosition().equals(new Vector2d(4, 0)));
        assertEquals(mA.toString(), "v");
    }

    public boolean properValue(String input) {
        switch (input) {
            case "f", "forward", "b", "backward", "r", "right", "l", "left" -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    @Test
    public void parseTest(){
        String[] correct = {"b", "backward", "f", "forward", "r", "right", "l", "left"};
        String[] wrong = {"bac", "do tyłu", "przód", "forw", "turn right", "prawo", "leftt", "obróc w lewo"};

        for(String c : correct) assertTrue(properValue(c));
        for(String w : wrong) assertFalse(properValue(w));
    }
}
