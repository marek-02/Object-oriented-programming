package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
            GrassField map = new GrassField(10);
            Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
            IEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();

            GridPane gridPane = new GridPane();
            gridPane.setGridLinesVisible(true);
            int minX = map.getLowerLeftCorner().getX();
            int maxX = map.getUpperRightCorner().getX();
            int minY = map.getLowerLeftCorner().getY();
            int maxY = map.getUpperRightCorner().getY();
            Label indexZero = new Label("y/x");
            int width = 20;
            int height = 20;
            gridPane.add(indexZero, 0, 0, 1, 1);
            gridPane.getColumnConstraints().add(new ColumnConstraints(width));
            gridPane.getRowConstraints().add(new RowConstraints(height));

            int start = 1;
            int i = minX;
            while (i <= maxX) {
                Label c = new Label("" + i);
                gridPane.add(c, start, 0, 1, 1);
                GridPane.setHalignment(c, HPos.CENTER);
                gridPane.getColumnConstraints().add(new ColumnConstraints(width));
                i++;
                start++;
            }

            start = 1;
            i = maxY;
            while (i >= minY) {
                Label r = new Label("" + i);
                gridPane.add(r, 0, start, 1, 1);
                GridPane.setHalignment(r, HPos.CENTER);
                gridPane.getRowConstraints().add(new RowConstraints(height));
                i--;
                start++;
            }
            Vector2d[] grassAndAnimals = map.getObserver().getxVectors().toArray(new Vector2d[0]);
            for (Vector2d position : grassAndAnimals) {
                Object object = map.objectAt(position);
                Label label = new Label(object.toString());
                gridPane.add(label, 1 + position.x - minX, 1 + maxY - position.y, 1, 1);
            }

            Scene scene = new Scene(gridPane, 400, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

