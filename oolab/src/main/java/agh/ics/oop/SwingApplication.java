package agh.ics.oop;

public class SwingApplication {
    public static void main(String[] args) {
        SwingMapChooser chooser = new SwingMapChooser();
        String decision = chooser.getDecision();
        System.out.println(decision);
        AbstractWorldMap map;
        if (decision.equals("yes") || decision.equals("y")) {
            SwingGrassCounter gc = new SwingGrassCounter();
            map = new GrassField(gc.getGrassCounter());
        }
        else
        {
            SwingMapDimensions mapDim = new SwingMapDimensions();
            map = new RectangularMap(mapDim.getyMap(), mapDim.getxMap());
        }


        SwingStartPositions positions = new SwingStartPositions();

        SwingCommands commands = new SwingCommands();
        MoveDirection[] directions = new OptionsParser().parse(commands.getCommands());

        SwingSimulationEngine engine = new SwingSimulationEngine(directions, map, positions.getPositions().toArray(new Vector2d[0]));
    }
}
