package com.umlcc.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UmlccApplication extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("UML Compliance Checker for Java");
        scene = new Scene(loadFXML("home"), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UmlccApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
