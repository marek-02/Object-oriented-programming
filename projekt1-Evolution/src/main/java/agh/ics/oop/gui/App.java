package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class App extends Application {
    private WorldMap map;

    @Override
    public void start(Stage primaryStage) {
        Button b = new Button("Start");
        b.setPrefHeight(50);
        b.setVisible(true);
        b.setDefaultButton(true);
        Button b2= new Button("Stop");
        b2.setPrefHeight(50);
        b2.setVisible(true);
        GridPane gridPane = new GridPane();
        VBox vBox = new VBox(20);
        GuiSimulationEngine engine = new GuiSimulationEngine(gridPane, vBox, map, 100);
        Thread t1 = new Thread(engine);
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                t1.start();

            }
        });
//        b2.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                t1.currentThread().interrupt();
//                engine.stop();
//                System.out.println(t1);
//            }
//        });
        HBox h1 = new HBox(20);
        VBox v2 = new VBox(20, makeLegend(), vBox);
        h1.getChildren().addAll(v2, gridPane);
        VBox v1 = new VBox(20);
        v1.getChildren().addAll(b, b2, h1);
        Scene s1 = new Scene(v1, 1000, 1000);
        primaryStage.setScene(s1);
        primaryStage.show();
    }

    public void init() {
        int width = 25;
        int height = 25;
        int energyFromPlant = 15;
        int startPlants = 16;
        int dayPlants = 4;
        int startAnimals = 25;
        int startEnergy = 60;
        int fullEnergy = 45;
        int birthEnergy = 25;
        int minMutation = 1;
        int maxMutation = 4;
        int genomLength = 10;
        TypeOfPlants typeOfPlants = TypeOfPlants.EQATOR;
        TypeOfMutation typeOfMutation = TypeOfMutation.RANDOM;
        TypeOfMovement typeOfMovement = TypeOfMovement.CRAZY;
        TypeOfPlanet typeOfPlanet = TypeOfPlanet.PORTAL;
        map = new WorldMap(width, height, energyFromPlant, startPlants, dayPlants, startAnimals, startEnergy, fullEnergy,
                                    birthEnergy, minMutation, maxMutation,
                                        genomLength, typeOfPlants, typeOfMutation, typeOfMovement, typeOfPlanet);

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

    private VBox makeLegend() {
        Label legend = new Label("Legend:");

        HBox hboxStep = rectangleLegend("Steppe Field", 234, 231, 177);
        HBox hboxJungle = rectangleLegend("Jungle Field", 97, 135, 110);
        HBox hboxGrass = rectangleLegend("Grass Field", 166, 187, 141);

        HBox hboxWeak = circleleLegend("Weak Animal", 167, 121, 121);
        HBox hboxNormal = circleleLegend("Normal Animal", 112, 79, 79);
        HBox hboxFed = circleleLegend("Fed Animal", 85, 57, 57);
        HBox hboxStrong = circleleLegend("Strong Animal", 71, 45, 45);

        VBox vbox = new VBox(20, legend, hboxStep, hboxJungle, hboxGrass, hboxWeak, hboxNormal, hboxFed, hboxStrong);

        return vbox;
    }
}
