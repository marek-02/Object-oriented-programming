package agh.ics.oop;

import java.util.Arrays;

public class World {
    public static void main(String[] args) {
        System.out.println("system wystartował");
        run(stringToDirections(args));
        System.out.println("system zakończył działanie");
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
                default -> System.out.println("Zwierzak idzie do przodu");
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
        for(int i = 0; i < realLength; i++) {
            switch (dirs[i]) {
                case "f" -> result[i] = Direction.FORWARD;
                case "b" -> result[i] = Direction.BACKWARD;
                case "r" -> result[i] = Direction.RIGHT;
                case "l" -> result[i] = Direction.LEFT;
                default -> result[i] = null;
            }
        }
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