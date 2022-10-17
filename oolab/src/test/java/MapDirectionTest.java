import agh.ics.oop.MapDirection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {

    @Test
    public void testNext(){
        assertEquals(MapDirection.NORTH.next(), MapDirection.EAST);
        assertEquals(MapDirection.EAST.next(), MapDirection.SOUTH);
        assertEquals(MapDirection.SOUTH.next(), MapDirection.WEST);
        assertEquals(MapDirection.WEST.next(), MapDirection.NORTH);
        assertNotEquals(MapDirection.NORTH.next(), MapDirection.NORTH);
        assertNotEquals(MapDirection.EAST.next(), MapDirection.NORTH);
        assertNotEquals(MapDirection.WEST.next(), null);
        assertNotEquals(MapDirection.SOUTH.next(), MapDirection.NORTH);
    }

    @Test
    public void testPrevious(){
        assertEquals(MapDirection.NORTH.previous(), MapDirection.WEST);
        assertEquals(MapDirection.EAST.previous(), MapDirection.NORTH);
        assertEquals(MapDirection.SOUTH.previous(), MapDirection.EAST);
        assertEquals(MapDirection.WEST.previous(), MapDirection.SOUTH);
        assertNotEquals(MapDirection.NORTH.previous(), MapDirection.NORTH);
        assertNotEquals(MapDirection.EAST.previous(), MapDirection.SOUTH);
        assertNotEquals(MapDirection.WEST.previous(), null);
        assertNotEquals(MapDirection.SOUTH.previous(), MapDirection.NORTH);
    }
}