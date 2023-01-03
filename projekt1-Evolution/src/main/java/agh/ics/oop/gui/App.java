package agh.ics.oop.gui;

import agh.ics.oop.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import org.json.*;



public class App extends Application {
    private WorldMap smallMap;
    private WorldMap normalMap;
    private WorldMap hugeMap;
    private WorldMap currentMap;
    private int width;
    private int height;
    private int energyFromPlant;
    private int startPlants;
    private int dayPlants;
    private int startAnimals;
    private int startEnergy;
    private int fullEnergy;
    private int birthEnergy;
    private int minMutation;
    private int maxMutation;
    private int genomLength;
    private TypeOfPlants typeOfPlants;
    private TypeOfMutation typeOfMutation;
    private TypeOfMovement typeOfMovement;
    private TypeOfPlanet typeOfPlanet;
    private boolean csv;

    @Override
    public void start(Stage primaryStage) {
        Button newSimulation = new Button("Start selected simulation");
        newSimulation.setDisable(true);
        final Label message = new Label("You can choose configuration file or one of the default configurations and then start simulation.");
        ChoiceBox confiugarions = new ChoiceBox(FXCollections.observableArrayList("Small", "Normal", "Huge", new Separator(), "Select configuration from JSON file"));
        CheckBox csvCheckBox = new CheckBox("Safe daily data to CSV file");
        ArrayList<Thread> threads = new ArrayList<>();

        EventHandler newSimulationEvent = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int myIndex = threads.size();
                WorldMap map = new WorldMap(currentMap);
                GuiSimulationEngine engine = new GuiSimulationEngine(map, csv, 100);
                threads.add(new Thread(engine));


                confiugarions.setValue(null);
                reset();
                newSimulation.setDisable(true);
                message.setText("You can choose configuration file or one of the default configurations and then start simulation.");


                Stage stage = new Stage();
                VBox infos = new VBox(20, engine.getMapInfoView(), engine.getTrackedAnimalInfo());
                HBox legendAndMapAndInfox = new HBox(20, engine.getLegend(), engine.getMapView(), infos);

                Button startButton = new Button("Start");
                startButton.setPrefHeight(50);
                startButton.setVisible(true);
                Button stopButton = new Button("Stop");
                stopButton.setPrefHeight(50);
                stopButton.setDisable(true);

                startButton.setDefaultButton(true);
                EventHandler startSimulation = new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        engine.go();
                        threads.set(myIndex, new Thread(engine));
                        threads.get(myIndex).start();

                        startButton.setDisable(true);
                        stopButton.setDisable(false);
                    }
                };
                startButton.setOnAction(startSimulation);
                EventHandler stopSimulation = new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        engine.stop();
                        threads.get(myIndex).interrupt();
                        startButton.setDisable(false);
                        stopButton.setDisable(true);
                    }
                };
                stopButton.setOnAction(stopSimulation);
                HBox buttons = new HBox(20, startButton, stopButton);
                VBox everything = new VBox(20, buttons, legendAndMapAndInfox);

                stage.setScene(new Scene(everything));
                stage.setWidth(10 * map.getWidth() + 750);
                stage.setHeight(Math.max(10 * map.getHeight(), 700));
                stage.show();
            }
        };

        confiugarions.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() == 0) {
                    newSimulation.setDisable(false);
                    currentMap = smallMap;
                }
                else if(newValue.intValue() == 1) {
                    newSimulation.setDisable(false);
                    currentMap = normalMap;
                }
                else if(newValue.intValue() == 2) {
                    newSimulation.setDisable(false);
                    currentMap = hugeMap;
                }
                else if(newValue.intValue() == 4) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Select JSON file with configuration");
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files", "*.json");
                    fileChooser.getExtensionFilters().add(extFilter);
                    Stage stageFileChooser = new Stage();
                    stageFileChooser.setWidth(400);
                    stageFileChooser.setHeight(400);
                    File selectedFile = fileChooser.showOpenDialog(stageFileChooser);

                    if(selectedFile != null) {
                        try {
                            String jsonString = "";
                            try (BufferedReader reader = new BufferedReader(new FileReader(new File(selectedFile.getPath())))) {
                                String line;
                                while ((line = reader.readLine()) != null)
                                    jsonString += line + '\n';
                            } catch (IOException e) {
                                throw new JSONException("Couldn't read selected file.");
                            }
                            JSONObject jsonObject = new JSONObject(jsonString);
                            width = getSafeInt(jsonObject, "width", width);
                            height = getSafeInt(jsonObject, "height", height);
                            energyFromPlant = getSafeInt(jsonObject, "energyFromPlant", energyFromPlant);
                            startPlants = getSafeInt(jsonObject, "startPlants", startPlants);
                            dayPlants = getSafeInt(jsonObject, "dayPlants", dayPlants);
                            startAnimals = getSafeInt(jsonObject, "startAnimals", startAnimals);
                            startEnergy = getSafeInt(jsonObject, "startEnergy", startEnergy);
                            fullEnergy = getSafeInt(jsonObject, "fullEnergy", fullEnergy);
                            birthEnergy = getSafeInt(jsonObject, "birthEnergy", birthEnergy);
                            minMutation = getSafeInt(jsonObject, "minMutation", minMutation);
                            maxMutation = getSafeInt(jsonObject, "maxMutation", maxMutation);
                            genomLength = getSafeInt(jsonObject, "genomLength", genomLength);
                            setSafePlants(jsonObject, "typeOfPlants");
                            setSafeMutations(jsonObject, "typeOfMutation");
                            setSafeMovement(jsonObject, "typeOfMovement");
                            setSafePlanet(jsonObject, "typeOfPlanet");
                            if(!checkValues()) {
                                message.setText("Data from the selected file failed validation! \nYou can select another configuration file or one of the default configurations.");
                                reset();
                                confiugarions.setValue(null);
                            } else {
                                currentMap = new WorldMap(width, height, energyFromPlant, startPlants, dayPlants, startAnimals, startEnergy, fullEnergy,
                                        birthEnergy, minMutation, maxMutation,genomLength,
                                        typeOfPlants, typeOfMutation, typeOfMovement, typeOfPlanet);
                                newSimulation.setDisable(false);
                                message.setText("Data validation from the selected file was successful! \nYou can start the simulation or choose another configuration file or one of the default configurations.");
                            }
                        } catch (JSONException ex) {
                            message.setText("The selected file should be JSON type. You can select configuration file or one of the default configurations.");
                            confiugarions.setValue(null);
                        }
                    }
                }
            }
        });

        csvCheckBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(csvCheckBox.isSelected()) csv = true;
                else csv = false;
            }
        });

        newSimulation.setOnAction(newSimulationEvent);
        HBox choosers = new HBox(30, confiugarions, newSimulation, csvCheckBox);
        VBox startView = new VBox(choosers, message);
        Scene s1 = new Scene(startView, 600, 150);
        primaryStage.setScene(s1);
        primaryStage.show();
    }

    private int getSafeInt(JSONObject obj, String key, int oldValue) {
        try {
            return obj.getInt(key);
        } catch (JSONException ex) {
            return oldValue;
        }
    }

    private void setSafePlants(JSONObject obj, String key) {
        try {
            String s = obj.getString(key).toLowerCase();
            if(s.equals("corpses")) typeOfPlants = TypeOfPlants.CORPSES;
            else typeOfPlants = TypeOfPlants.EQATOR;
        } catch (JSONException ex) {}
    }
    private void setSafeMutations(JSONObject obj, String key) {
        try {
            String s = obj.getString(key).toLowerCase();
            if(s.equals("random")) typeOfMutation =TypeOfMutation.RANDOM;
            else typeOfMutation = TypeOfMutation.CORRECTION;
        } catch (JSONException ex) {}
    }

    private void setSafeMovement(JSONObject obj, String key) {
        try {
            String s = obj.getString(key).toLowerCase();
            if(s.equals("crazy")) typeOfMovement = TypeOfMovement.CRAZY;
            else typeOfMovement = TypeOfMovement.NORMAL;
        } catch (JSONException ex) { }
    }

    private void setSafePlanet(JSONObject obj, String key) {
        try {
            String s = obj.getString(key).toLowerCase();
            if(s.equals("portal")) typeOfPlanet = TypeOfPlanet.PORTAL;
            else typeOfPlanet = TypeOfPlanet.EARTH;
        } catch (JSONException ex) {}
    }

    private boolean checkValues() {
        return width > 1 && height > 1 && energyFromPlant > 0 && startPlants > 0 && dayPlants > 0 && startAnimals > 0 && startEnergy > 1 &&
                birthEnergy > 0 && fullEnergy > birthEnergy && minMutation >= 0 && maxMutation >= minMutation && genomLength > 0;
    }

    private void reset() {
        width = 15;
        height = 15;
        energyFromPlant = 7;
        startPlants = 10;
        dayPlants = 3;
        startAnimals = 14;
        startEnergy = 35;
        fullEnergy = 20;
        birthEnergy = 15;
        minMutation = 1;
        maxMutation = 4;
        genomLength = 12;
        typeOfPlants = TypeOfPlants.EQATOR;
        typeOfMutation = TypeOfMutation.CORRECTION;
        typeOfMovement = TypeOfMovement.NORMAL;
        typeOfPlanet = TypeOfPlanet.EARTH;
        csv = false;
    }
    public void init() {
        reset();
        smallMap = new WorldMap(width, height, energyFromPlant, startPlants, dayPlants, startAnimals, startEnergy, fullEnergy,
                birthEnergy, minMutation, maxMutation,genomLength,
                typeOfPlants, typeOfMutation, typeOfMovement, typeOfPlanet);
        normalMap = new WorldMap(30, 30, 15, 20, 4, 30, 50, 25,
                18, 0, 1,12,
                TypeOfPlants.CORPSES, TypeOfMutation.CORRECTION, TypeOfMovement.NORMAL, TypeOfPlanet.PORTAL);
        hugeMap = new WorldMap(60, 50, 25, 30, 6, 60, 70, 40,
                25, 0, 2,
                12, TypeOfPlants.EQATOR, TypeOfMutation.RANDOM, TypeOfMovement.CRAZY, TypeOfPlanet.PORTAL);
        currentMap = smallMap;
    }
}
