package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class AnimalTest {

    @Test
    public void orientationTest(){
        Animal oA = new Animal();
        assertEquals(oA.toString(), "Położenie: (2,2), orientacja: Północ");
        oA.move(MoveDirection.LEFT);
        assertEquals(oA.toString(), "Położenie: (2,2), orientacja: Zachód");
        oA.move(MoveDirection.RIGHT);
        oA.move(MoveDirection.RIGHT);
        assertEquals(oA.toString(), "Położenie: (2,2), orientacja: Wschód");
        oA.move(MoveDirection.RIGHT);
        assertNotEquals(oA.toString(), "Położenie: (2,2), orientacja: Wschód");
        oA.move(MoveDirection.LEFT);
        oA.move(MoveDirection.LEFT);
        oA.move(MoveDirection.LEFT);
        assertNotEquals(oA.toString(), "Położenie: (2,2), orientacja: Północ");
        oA.move(MoveDirection.RIGHT);
        assertNotEquals(oA.toString(), "Położenie: (2,2), orientacja: Południe");
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
        assertEquals(mA.toString(), "Położenie: (2,4), orientacja: Północ");
        mA.move(MoveDirection.LEFT);
        mA.move(MoveDirection.FORWARD);
        mA.move(MoveDirection.FORWARD);
        mA.move(MoveDirection.FORWARD);
        assertEquals(mA.toString(), "Położenie: (0,4), orientacja: Zachód");
        mA.move(MoveDirection.BACKWARD);
        mA.move(MoveDirection.BACKWARD);
        mA.move(MoveDirection.BACKWARD);
        mA.move(MoveDirection.BACKWARD);
        mA.move(MoveDirection.BACKWARD);
        mA.move(MoveDirection.BACKWARD);
        assertEquals(mA.toString(), "Położenie: (4,4), orientacja: Zachód");
        mA.move(MoveDirection.LEFT);
        mA.move(MoveDirection.FORWARD);
        mA.move(MoveDirection.FORWARD);
        mA.move(MoveDirection.FORWARD);
        mA.move(MoveDirection.FORWARD);
        mA.move(MoveDirection.FORWARD);
        assertEquals(mA.toString(), "Położenie: (4,0), orientacja: Południe");
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
