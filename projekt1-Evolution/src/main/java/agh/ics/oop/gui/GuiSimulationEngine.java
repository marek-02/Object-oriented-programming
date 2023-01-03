package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.Field;
import agh.ics.oop.WorldMap;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class GuiSimulationEngine implements Runnable {
    private GridPane gridPane;
    private VBox vBox;
    private VBox vBox2;
    private WorldMap map;
    private int width;
    private int height;
    private boolean stop;
    private int moveDelay;
    private VBox legend;
    private Animal trackedAnimal;
    private boolean csv;
    private File csvFile;
    private String csvString;

    public GuiSimulationEngine(WorldMap worldMap, boolean csv, int moveDelay) {
        this.gridPane =  new GridPane();
        this.vBox = new VBox(5);
        this.vBox2 = new VBox(5);
        this.map = worldMap;
        this.csv = csv;
        if(csv) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select CSV file for output data");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);
            csvFile = fileChooser.showSaveDialog(null);
            csvString = "";
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
                csvString += "day; number of plants; number of animals; number of free fields; the most popular genotype; average energy; average life length of deads\n";
                writer.write(csvString);
                writer.close();
            } catch (Exception ex) {
                this.csv = false;
                System.out.println("Wrong csv file.");
            }
        }

        this.width = 10;
        this.height = 10;
        this.moveDelay = moveDelay;
        this.stop = true;
        this.trackedAnimal = null;
        this.legend = makeLegend();
        createMap();
        gridPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(stop) {
                    Node source;
                    if(e.getTarget() instanceof Circle) {
                        source = (Node)((Circle) e.getTarget()).getParent();
                        int x = GridPane.getColumnIndex(source);
                        int y = GridPane.getRowIndex(source);
                        trackedAnimal = map.getRandomAnimalFromField(x, y);
                    } else trackedAnimal = null;
                }
            }
        });
    }

    @Override
    public void run() {
        while(!stop) {
            Platform.runLater(()->{
                map.day();
                drawMap();
                drawInfo();
                drawInfoAboutTrackedAnimal();
                if(csv) addToCsv();
            });
            try { Thread.sleep(moveDelay); } catch (InterruptedException ex) { System.out.println(ex.getMessage()); }
        }
    }

    public GridPane getMapView() {
        return gridPane;
    }

    public VBox getMapInfoView() {
        return vBox;
    }

    public VBox getTrackedAnimalInfo() {
        return vBox2;
    }

    public VBox getLegend() {
        return legend;
    }

    public synchronized void go() {
        stop = false;
    }
    public synchronized void stop() {
        stop = true;
        drawMap();
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
        drawInfoAboutTrackedAnimal();
    }

    private void drawMap() {
        for(int y = 0; y < map.getHeight(); y++)
            for(int x = 0; x < map.getWidth(); x++) {
                drawField(x, y);
            }
    }
    private Circle drawAnimal(Animal a) {
        Circle circle = new Circle(width / 2 - 1);
        int r = 167;
        int gb = 121;
        if(a.equals(trackedAnimal)) {
            r = 255;
            gb = 0;
        } else if (stop && Arrays.equals(a.getGens(), map.getMostPopularGenotype())) {
            r = 104;
            gb = 170;
        } else if(a.isNormal(map.getBirthEnergy(), map.getFullEnergy())) {
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
            ArrayList<Animal> animals = map.getAnimalsFromField(x, y);
            Animal a = null;
            if(trackedAnimal != null && animals.contains(trackedAnimal)) a = trackedAnimal;
            else if(stop) {
                for(Animal animal : animals) {
                    if(animal.getGens().equals(map.getMostPopularGenotype())) {
                        a = animal;
                        break;
                    }
                }
            }
            if(a == null) a = animals.get(0);
            Circle c = drawAnimal(a);
            pane.getChildren().add(c);
            c.setCenterX(width/2);
            c.setCenterY(height/2);
        }
        gridPane.add(pane, x, y, 1, 1);
    }

    private void drawInfo() {
        Label l0 = new Label("Map informnation:");
        Label l1 = new Label("Day: " + map.getDay());
        Label l2 = new Label("Number of plants: " + map.getNumberOfPlants());
        Label l3 = new Label("Number of animals on the map: " + map.getNumberOfAnimalsOnTheMap());
        Label l4 = new Label("Number of free fields: " + map.getNumberOfFreeFields());
        Label l5 = new Label("The most popular genotype: " + map.getMostPopularGenotypeAsString());
        Label l6 = new Label("Average energy for living animals: " + map.averageEnergyForLiving());
        Label l7 = new Label("Average life length of dead animals: " + map.averageLifeTimeForDeads());

        vBox.getChildren().clear();
        vBox.getChildren().addAll(l0, l1, l2, l3, l4, l5, l6, l7);
    }

    private void drawInfoAboutTrackedAnimal() {
        vBox2.getChildren().clear();
        if(trackedAnimal != null) {
            Label l0 = new Label("Information about the tracked animal:");
            Label l1 = new Label("Genotype:\n" + trackedAnimal.getStringGens());
            Label l2 = new Label("Energy: " + trackedAnimal.getEnergy());
            Label l3 = new Label("Eaten plants: " + trackedAnimal.getEatenPlants());
            Label l4 = new Label("Number of children: " + trackedAnimal.getKids());
            Label l5;
            if(trackedAnimal.isAlive()) l5 = new Label("Age: " + trackedAnimal.getAge());
            else l5 = new Label("Death day: " + trackedAnimal.getDeathDay());

            vBox2.getChildren().addAll(l0, l1, l2, l3, l4, l5);
        } else {
            Label l = new Label("To select an animal to track, stop the simulation and click on the choosen animal");
            vBox2.getChildren().addAll(l);
        }

    }

    private VBox makeLegend() {
        Label legend = new Label("Legend:");

        HBox hboxStep = rectangleLegend("Steppe Field", 234, 231, 177);
        HBox hboxJungle = rectangleLegend("Jungle Field", 97, 135, 110);
        HBox hboxGrass = rectangleLegend("Grass Field", 166, 187, 141);

        HBox hboxWeak = circleleLegend("Weak Animal", 167, 121, 121);
        HBox hboxNormal = circleleLegend("Normal Animal", 112, 79, 79);
        HBox hboxFed = circleleLegend("Fed Animal", 85, 57, 57);
        HBox hboxStrong = circleleLegend("Strong Animal", 71, 45, 45);
        HBox hboxTracked = circleleLegend("Tracked Animal", 255,0, 0);
        HBox hboxPopular = circleleLegend("Animal with the most popular genotype", 104,170, 170);

        VBox vbox = new VBox(20, legend, hboxStep, hboxJungle, hboxGrass, hboxWeak, hboxNormal, hboxFed, hboxStrong, hboxTracked, hboxPopular);

        return vbox;
    }

    private HBox rectangleLegend(String infoText, int r, int g, int b) {
        Label info = new Label(infoText);
        Rectangle view = new Rectangle(20,20);
        view.setFill(Color.rgb(r, g, b));
        HBox hbox = new HBox(20);
        hbox.getChildren().addAll(view, info);
        return hbox;
    }

    private HBox circleleLegend(String infoText, int r, int g, int b) {
        Label info = new Label(infoText);
        Circle view = new Circle(10);
        view.setFill(Color.rgb(r, g, b));
        HBox hbox = new HBox(20);
        hbox.getChildren().addAll(view, info);
        return hbox;
    }

    private void addToCsv() {
        try {
            csvString += map.getDay() + "; " + map.getNumberOfPlants() + "; " + map.getNumberOfAnimalsOnTheMap() + "; " + map.getNumberOfFreeFields() +
                    "; " + map.getMostPopularGenotypeAsString() + "; " + map.averageEnergyForLiving() + "; " + map.averageLifeTimeForDeads() + '\n';
            BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(csvString);
            writer.close();
        } catch (Exception ex) {
            System.out.println("Problems with CSV file.");
        }
    }
}
