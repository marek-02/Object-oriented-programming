package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.Field;
import agh.ics.oop.WorldMap;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GuiSimulationEngine implements Runnable {
    private GridPane gridPane;
    private VBox vBox;
    private WorldMap map;
    private int width;
    private int height;
    private boolean stop;
    private int moveDelay;

    public GuiSimulationEngine(GridPane gridPane, VBox vBox, WorldMap worldMap, int moveDelay) {
        this.gridPane = gridPane;
        this.vBox = vBox;
        this.map = worldMap;
        this.width = 10;
        this.height = 10;
        this.moveDelay = moveDelay;
        this.stop = false;
        createMap();
    }

    @Override
    public void run() {
        while(!stop) {
            Platform.runLater(()->{
                map.day();
                drawMap();
                drawInfo();
            });
            try { Thread.sleep(moveDelay); } catch (InterruptedException ex) { System.out.println(ex.getMessage()); }
        }
    }

    public boolean isStopped() { return stop; }

    public void start() {
        stop = false;
    }
    public void stop() {
        stop = true;
    }

    private void createMap() {
        int maxX = map.getWidth() - 1;
        int maxY = map.getHeight() - 1;
        int i = 0;
        while (i <= maxX) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(width));
            i++;
        }
        i = maxY;
        while (i >= 0) {
            gridPane.getRowConstraints().add(new RowConstraints(height));
            i--;
        }
        drawMap();
        drawInfo();
    }

    private void drawMap() {
        for(int y = 0; y < map.getHeight(); y++)
            for(int x = 0; x < map.getWidth(); x++) {
                drawField(x, y);
            }
    }
    private Circle drawAnimal(Animal a) {
        Circle circle = new Circle(width / 2);
        int r = 167;
        int gb = 121;
        if(a.isNormal(map.getBirthEnergy(), map.getFullEnergy())) {
            r = 112;
            gb = 79;
        } else if(a.isFed(map.getFullEnergy())) {
            r = 85;
            gb = 57;
        } else if(a.isStrong(map.getFullEnergy())) {
            r = 71;
            gb = 45;
        }

        circle.setFill(Color.rgb(r, gb, gb));
        return circle;
    }

    private Pane drawGround(Field f) {
        Pane pane = new Pane();
        int r = 234;
        int g = 231;
        int b = 177;
        if(f.hasPlant()) {
            r = 166;
            g = 187;
            b = 141;
        } else if(f.isAttractive()) {
            r = 97;
            g = 135;
            b = 110;
        }
        pane.setStyle("-fx-background-color: rgb(" + r + "," + g + "," +  b + ");");
        return pane;
    }
    private void drawField(int x, int y) {
        Pane pane = drawGround(map.getField(x, y));
        if(map.getAnimalsFromField(x, y).size() > 0) {
            Animal a = map.getAnimalsFromField(x, y).get(0);
            Circle c = drawAnimal(a);
            pane.getChildren().add(c);
            c.setCenterX(width/2);
            c.setCenterY(height/2);
        }
        gridPane.add(pane, x, y, 1, 1);
    }

    private void drawInfo() {
        Label l0 = new Label("Day: " + map.getDay());
        Label l1 = new Label("Number of plants: " + map.getNumberOfPlants());
        Label l2 = new Label("Number of animals on the map: " + map.getNumberOfAnimalsOnTheMap());
        Label l3 = new Label("Number of free fields: " + map.getNumberOfFreeFields());
        Label l4 = new Label("The most popular genotype: " + map.getMostPopularGenotype());
        Label l5 = new Label("Average energy for living animals: " + map.averageEnergyForLiving());
        Label l6 = new Label("Average life length of dead animals: " + map.averageLifeTimeForDeads());

        vBox.getChildren().clear();
        vBox.getChildren().addAll(l0, l1, l2, l3, l4, l5, l6);
    }
}
