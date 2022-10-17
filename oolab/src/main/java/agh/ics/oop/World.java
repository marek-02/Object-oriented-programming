package agh.ics.oop;

import java.util.Arrays;

public class World {
    public static void main(String[] args) {
        //run(stringToDirections(args));
        Vector2d position1 = new Vector2d(1,2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2,1);
        System.out.println(position2);
        System.out.println(position1.add(position2));

        MapDirection map = MapDirection.NORTH;
        System.out.println(map.toString());
        map = map.next();
        System.out.println(map.toString());
        map = map.previous();
        System.out.println(map.toString());
        System.out.println(map.toUnitVector().toString());
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
        //return new Direction[] {Direction.FORWARD, Direction.BACKWARD};
        return result;
    }
}



//public static void run(String[] args){
//        System.out.println("zwierzak idzie do przodu");
//        for(int i = 0; i < args.length - 1; i++)
//            System.out.printf("%s, ", args[i]);
//        if (args.length > 0)
//            System.out.println(args[args.length - 1]);

//        System.out.println("Start");
//        for(String argument : args)
//        {
//            switch (argument) {
//                case "f":
//                    System.out.println("Zwierzak idzie do przodu");
//                    break;
//                case "b":
//                    System.out.println("Zwierzak idzie do tyłu");
//                    break;
//                case "r":
//                    System.out.println("Zwierzak skręca w prawo");
//                    break;
//                case "l":
//                    System.out.println("Zwierzak skręca w lewo");
//                    break;
//                default:
//                    System.out.println("Zwierzak idzie do przodu");
//            };
//        }
//        System.out.println("Stop");
//  }