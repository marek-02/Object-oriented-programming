package agh.ics.oop;

import agh.ics.oop.gui.App;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }

    public static void run(Direction[] dirs){
        System.out.println("Start");
        for(Direction direction : dirs)
        {
            switch (direction) {
                case FORWARD -> System.out.println("Zwierzak idzie do przodu");
                case BACKWARD -> System.out.println("Zwierzak idzie do tyłu");
                case RIGHT -> System.out.println("Zwierzak skręca w prawo");
                case LEFT -> System.out.println("Zwierzak skręca w lewo");
                default -> throw new IllegalStateException("Zła tablica argumentów");
            }

        }
        System.out.println("Stop");
    }
    public static Direction[] stringToDirections(String[] dirs) {

        String[] correct = {"f",  "b", "r", "l"};
        int realLength = 0;
        for (String dir : dirs)
            for (String ok : correct)
                if(dir.equals(ok)) {
                    realLength++;
                    break;
                }

        Direction[] result = new Direction[realLength];

        int i = 0;
        for(String dir : dirs) {
            switch (dir) {
                case "f" -> result[i++] = Direction.FORWARD;
                case "b" -> result[i++] = Direction.BACKWARD;
                case "r" -> result[i++] = Direction.RIGHT;
                case "l" -> result[i++] = Direction.LEFT;
                default -> System.out.println("Niepoprawne polecenie");
            }
        }
        return result;
    }
}