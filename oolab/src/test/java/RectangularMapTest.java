import agh.ics.oop.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    @Test
    public void runTest1(){
        MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        //IEngine engine = new SimulationEngine(directions, map, positions);
        //engine.run();
        assertTrue(map.isOccupied(new Vector2d(2, 0)));
        assertTrue(map.isOccupied(new Vector2d(3, 7)));
        assertFalse(map.isOccupied(new Vector2d(2, 2)));
        assertFalse(map.isOccupied(new Vector2d(3, 4)));
        assertFalse(map.isOccupied(new Vector2d(2, -1)));
        assertFalse(map.isOccupied(new Vector2d(3, 3)));
    }

    @Test
    public void runTest2(){
        MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "f", "f", "f", "r", "l", "r", "l", "f", "f", "r", "r", "f", "f", "b", "b", "f"});
        IWorldMap map = new RectangularMap(6, 6);
        Vector2d[] positions = { new Vector2d(0,1), new Vector2d(4, 4), new Vector2d(2, 3) }; //new Vector2d(4, 4), musiałem wykomentować jeszcze tego vectora bo throwuje ex
        //IEngine engine = new SimulationEngine(directions, map, positions);
        //engine.run();
        assertTrue(map.isOccupied(new Vector2d(1, 3)));
        assertTrue(map.isOccupied(new Vector2d(2, 3)));
        assertTrue(map.isOccupied(new Vector2d(6, 5)));
        assertFalse(map.isOccupied(new Vector2d(0, 1)));
        assertFalse(map.isOccupied(new Vector2d(2, 5)));
        assertFalse(map.isOccupied(new Vector2d(2, 4)));
        assertFalse(map.isOccupied(new Vector2d(3, 3)));
    }
}
