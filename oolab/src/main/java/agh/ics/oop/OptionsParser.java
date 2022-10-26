package agh.ics.oop;

public class OptionsParser {
    public MoveDirection[] parse(String[] dirs) {
        String[] correct = {"b", "backward", "f", "forward", "r", "right", "l", "left"};
        int realLength = 0;

        for(String dir : dirs)
            for(String ok : correct)
            {
                if(dir.equals(ok)){
                    realLength++;
                    break;
                }
            }
        MoveDirection[] result = new MoveDirection[realLength];

        int i = 0;
        for(String dir : dirs) {
            switch (dir) {
                case "f", "forward" -> result[i++] = MoveDirection.FORWARD;
                case "b", "backward" -> result[i++] = MoveDirection.BACKWARD;
                case "r", "right" -> result[i++] = MoveDirection.RIGHT;
                case "l", "left" -> result[i++] = MoveDirection.LEFT;
//                default -> System.out.println("Niepoprawne polecenie");
            }
        }
        return result;
    }
}
