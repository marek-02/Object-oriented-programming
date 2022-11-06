package agh.ics.oop;

public class SwingApplication {
    public static void main(String[] args) {
        SwingMapDimensions mapDim = new SwingMapDimensions();
        IWorldMap map = new RectangularMap(mapDim.getxMap(), mapDim.getyMap());

        SwingStartPositions positions = new SwingStartPositions();

        SwingCommands commands = new SwingCommands();
        MoveDirection[] directions = new OptionsParser().parse(commands.getCommands());

        SwingSimulationEngine engine = new SwingSimulationEngine(directions, map, positions.getPositions().toArray(new Vector2d[0]));
    }
}