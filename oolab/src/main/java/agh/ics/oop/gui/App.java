package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {
    private MoveDirection[] directions;
    private AbstractWorldMap map;
    private Vector2d[] positions;
    private int height;
    private int width;
    private int moveDelay;
    private GridPane gridPane;
    private Scene scene;
    private Button b;
    private TextField textField;
    private VBox vBox;
    @Override
    public void start(Stage primaryStage) {
        try {
            GuiSimulationEngine engine = new GuiSimulationEngine(directions, map, positions, primaryStage, gridPane, moveDelay, width, height);
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    directions = new OptionsParser().parse(new String[]{"f", "b", "l", "l", "b", "b"});
                    String newMoves = textField.getText();
                    engine.setDirections(new OptionsParser().parse(newMoves.split(" ")));
                    textField.setText("");
                    textField.requestFocus();
                    new Thread(engine).start();
                }
            };
            b.setOnAction(event);


            int minX = map.getLowerLeftCorner().getX();
            int maxX = map.getUpperRightCorner().getX();
            int minY = map.getLowerLeftCorner().getY();
            int maxY = map.getUpperRightCorner().getY();
            int sceneHeight = ((maxY - minY) + 5) * height;
            int sceneWidth = ((maxX - minX) + 5) * width;
            scene = new Scene(vBox, sceneWidth, sceneHeight);
            primaryStage.setScene(scene);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void init() {
        directions = new OptionsParser().parse(new String[]{"f", "b", "l", "l", "b", "b"});
        map = new GrassField(10);
        positions = new Vector2d[] { new Vector2d(0,0), new Vector2d(3,4) };
        moveDelay = 500;
        height = 50;
        width = 50;
        //////
        gridPane = new GridPane();
        textField = new TextField("");
        textField.setPrefWidth(300);
        textField.setPrefHeight(50);
        b = new Button("Start");
        b.setPrefHeight(50);
        b.setVisible(true);
        b.setDefaultButton(true);
        HBox hBox = new HBox(20);
        hBox.getChildren().add(textField);
        hBox.getChildren().add(b);
        vBox = new VBox(10);
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(gridPane);
    }
}

