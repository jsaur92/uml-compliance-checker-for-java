package com.umlcc.controller;

import com.umlcc.model.ComplianceCheckerApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class UmlccApplication extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("UML Compliance Checker for Java");
        scene = new Scene(loadFXML("home"), 600, 400);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            ComplianceCheckerApplication app = ComplianceCheckerApplication.getInstance();
            app.close();
        });
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UmlccApplication.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void changeScene(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Scene getScene() {
        return scene;
    }
}
