package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiSimulationEngine implements IEngine, IGrassFieldObserver, Runnable {
    private MoveDirection[] moves;
    private AbstractWorldMap map;
    private List<Animal> animals;
    private Map<Vector2d, ImageView> images;
    private Stage primaryStage;
    private GridPane gridPane;
    private int width;
    private int height;
    private int sceneWidth;
    private int sceneHeight;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;
    private int moveDelay;

    public GuiSimulationEngine(MoveDirection[] moves, AbstractWorldMap map, Vector2d[] vectors, Stage primaryStage, GridPane gridPane, int moveDelay, int width, int height){
        this.width = width;
        this.height = height;
        this.primaryStage = primaryStage;
        this.moves = moves;
        this.map = map;
        this.animals = new ArrayList<>();
        this.images = new HashMap<>();
        this.moveDelay = moveDelay;
        for(Vector2d v : vectors){
            Animal a = new Animal(map, v);
            if(map.place(a)) {
                animals.add(a);
                if(! (map instanceof GrassField)) a.addObserver(this);
            }
        }

        minX = map.getLowerLeftCorner().getX();
        maxX = map.getUpperRightCorner().getX();
        minY = map.getLowerLeftCorner().getY();
        maxY = map.getUpperRightCorner().getY();
        sceneHeight = ((maxY - minY) + 5) * height;
        sceneWidth = ((maxX - minX) + 5) * width;
        this.gridPane = gridPane;

        primaryStage.show();
        drawMap();

        if(map instanceof GrassField) ((GrassField) map).addObserver(this);
    }

    @Override
    public void run() {
        int animalsLength = animals.size();
        for (int i = 0; i < moves.length; i++) {
            animals.get(i % animalsLength).move(moves[i]);
        }
    }
    private void drawMap() {
        gridPane.setGridLinesVisible(true);
        minX = map.getLowerLeftCorner().getX();
        maxX = map.getUpperRightCorner().getX();
        minY = map.getLowerLeftCorner().getY();
        maxY = map.getUpperRightCorner().getY();
        Label indexZero = new Label("y/x");
        gridPane.add(indexZero, 0, 0, 1, 1);
        GridPane.setHalignment(indexZero, HPos.CENTER);
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

        Vector2d[] mapElements = map.getMapElementPositions();
        for (Vector2d position : mapElements) {
            Object object = map.objectAt(position);
            GuiElementBox geb = new GuiElementBox();
            Image image = geb.getImageOfElement(object);
            ImageView imageView = new ImageView(image);
            images.put(position, imageView);
            imageView.setFitHeight(height - 4);
            imageView.setFitWidth(width - 4);
            gridPane.add(imageView, 1 + position.getX() - minX, 1 + maxY - position.getY(), 1, 1);
            GridPane.setHalignment(imageView, HPos.CENTER);
        }

        gridPane.setStyle("-fx-background-color: white;");
    }


    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){

        Platform.runLater(()->{
            removeElement(oldPosition);
            removeElement(newPosition);
            addElement(newPosition);
        });
        try { Thread.sleep(moveDelay); } catch (InterruptedException ex) { System.out.println(ex.getMessage()); }
    }
    public void addElement(Vector2d position){
        Platform.runLater(()->{
            int x = position.getX();
            int y = position.getY();
            if(x > maxX || x < minX || y > maxY || y < minY)
            {
                maxX = Math.max(x, maxX);
                minX = Math.min(x, minX);
                maxY = Math.max(y, maxY);
                minY = Math.min(y, minY);
                sceneHeight = ((maxY - minY) + 5) * height;
                sceneWidth = ((maxX - minX) + 5) * width;
                primaryStage.setWidth(sceneWidth);
                primaryStage.setHeight(sceneHeight);
                Node node = gridPane.getChildren().get(0); //pierwszy dzieciak trzyma linie
                gridPane.getChildren().clear();
                gridPane.getChildren().add(node);
                drawMap();
            }
            else {
                GuiElementBox geb = new GuiElementBox();
                ImageView newI = new ImageView(geb.getImageOfElement(map.objectAt(position)));
                newI.setFitHeight(height - 4);
                newI.setFitWidth(width - 4);
                images.put(position, newI);
                gridPane.add(newI, 1 + position.getX() - minX, 1 + maxY - position.getY(), 1, 1);
                GridPane.setHalignment(newI, HPos.CENTER);
            }
        });
    }

    public void removeElement(Vector2d position) {
        Platform.runLater(()->{
            ImageView i = images.remove(position);
            gridPane.getChildren().remove(i);
        });
    }
    public Vector2d getUpperRightCorner() {
//        for(Vector2d v : images.keySet()) {
//            if (v.getX() > maxX) maxX = v.getX();
//            if (v.getY() > maxX) maxY = v.getY();
//        }
        return new Vector2d(maxX, maxY);
    }

    public Vector2d getLowerLeftCorner() {
//        for(Vector2d v : images.keySet()) {
//            if (v.getX() < minX) minX = v.getX();
//            if (v.getY() < minX) minY = v.getY();
//        }
        return new Vector2d(minX, minY);
    }

    public void setDirections(MoveDirection[] newMoves) {
        moves = newMoves;
    }
}
