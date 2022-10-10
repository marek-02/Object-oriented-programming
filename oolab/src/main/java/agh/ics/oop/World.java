package agh.ics.oop;

public class World {
    public static void main(String[] args) {
        System.out.println("system wystartował");

        Direction[] dirs = new Direction[] {Direction.FORWARD, Direction.FORWARD, Direction.BACKWARD, Direction.RIGHT, Direction.LEFT};
        run(dirs);
        System.out.println("system zakończył działanie");
    }

    public static void run(Direction[] dirs){
                System.out.println("Start");
        for(Direction direction : dirs)
        {
            switch (direction) {
                case FORWARD:
                    System.out.println("Zwierzak idzie do przodu");
                    break;
                case BACKWARD:
                    System.out.println("Zwierzak idzie do tyłu");
                    break;
                case RIGHT:
                    System.out.println("Zwierzak skręca w prawo");
                    break;
                case LEFT:
                    System.out.println("Zwierzak skręca w lewo");
                    break;
                default:
                    System.out.println("Zwierzak idzie do przodu");
            };
        }
        System.out.println("Stop");
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