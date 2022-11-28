package agh.ics.oop;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public MoveDirection[] parse(String[] dirs) throws IllegalArgumentException {
        List<MoveDirection> result = new ArrayList<>();

        for(String dir : dirs) {
            switch (dir) {
                case "f", "forward" -> result.add(MoveDirection.FORWARD);
                case "b", "backward" -> result.add(MoveDirection.BACKWARD);
                case "r", "right" -> result.add(MoveDirection.RIGHT);
                case "l", "left" -> result.add(MoveDirection.LEFT);
                default -> throw new IllegalArgumentException(dir + " is not legal move specification");
            }
        }
        MoveDirection[] arrayResult = new MoveDirection[result.size()];
        arrayResult = result.toArray(arrayResult);
        return arrayResult;
    }
}
