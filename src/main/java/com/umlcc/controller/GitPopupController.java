package com.umlcc.controller;

import com.umlcc.model.ComplianceCheckerApplication;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;

public class GitPopupController {

    private static final int escapeCode = 27;
    private static String path;

    @FXML private TextArea outputText;
    @FXML private VBox root;
    @FXML private TextField targetText;
    @FXML private HBox templateBox;
    @FXML private Label templateLabel;
    @FXML private Button templateRepoButton;
    @FXML private TextField templateText;

    private ComplianceCheckerApplication app;
    private StringBuilder outputBuilder;

    @FXML
    private void initialize() {
        app = ComplianceCheckerApplication.getInstance();
        path = "";
        outputBuilder = new StringBuilder();
        Platform.runLater( () -> root.requestFocus() );
    }

    @FXML
    private void onClonePullClick(MouseEvent event) {
        app.attemptPullGitRepo(targetText.getText(), templateText.getText(), outputBuilder);
    }

    @FXML
    private void onCloneIntoClick(MouseEvent event) {
        templateText.setText(loadRemoteRepo());
    }

    @FXML
    private void checkEscapePress(KeyEvent event) {
        if (event.getCode().getCode() == escapeCode) {
            ((Stage)root.getScene().getWindow()).close();
        }
    }

    private String loadRemoteRepo() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Open repository to clone to / pull from");

        Scene scene = root.getScene();
        if (scene == null) return "";

        Stage stage = (Stage) scene.getWindow();
        File file = dirChooser.showDialog(stage);

        if (file == null) return "";
        return file.getAbsolutePath();
    }



    public static String getPath() {
        return path;
    }
}