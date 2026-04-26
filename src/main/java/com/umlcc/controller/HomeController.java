package com.umlcc.controller;

import com.umlcc.model.ComplianceCheckerApplication;
import com.umlcc.model.Directory;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

public class HomeController {
    @FXML private VBox root;
    @FXML private HBox templateBox;
    @FXML private Label templateLabel;
    @FXML private TextField templateText;
    @FXML private Button templateRepoButton;
    @FXML private TextField targetText;
    @FXML private TextArea outputText;

    private boolean adminView = true;
    private ComplianceCheckerApplication app;

    @FXML
    public void initialize() {
        updateLayout();
        app = ComplianceCheckerApplication.getInstance();
    }

    @FXML
    protected void onSettingsClick() {
        System.out.println("Open Settings");
    }

    @FXML
    protected void onTemplateFileClick() {
        String path = loadUmlccFile();
        templateText.setText(path);
    }

    @FXML
    protected void onTemplateRepoClick() {
        String path = loadTemplateRepo();
        templateText.setText(path);
    }

    @FXML
    protected void onTemplateGitClick() {

    }

    @FXML
    protected void onTargetRepoClick() {
        String path = loadTargetRepo();
        targetText.setText(path);
    }

    @FXML
    protected void onTargetGitClick() {

    }

    @FXML
    protected void onRunCheckerClick() {
        File f = new File(targetText.getText());
        if (f.exists()) {
            if (templateText.getText().endsWith(".umlcc")) {
                app.loadUmlDataByUmlcc(templateText.getText());
            } else {
                app.loadUmlDataByRepo(templateText.getText());
            }
            ArrayList<String> results = app.checkCompliance(targetText.getText());
            StringBuilder s = new StringBuilder();
            for (String r : results) {
                s.append(r).append("\n");
            }
            outputText.setText(s.toString());
        }
    }

    @FXML
    protected void onGenerateUmlccClick() {
        System.out.println("Generate .umlcc File");
    }

    @FXML
    protected void onOutputTextClick() {
        System.out.println("attempt copy");
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection data = new StringSelection(outputText.getText());
        cb.setContents(data, null);
    }

    protected void updateLayout() {
        if (adminView) {
            templateLabel.setText("Template Repository / File");
            if (! templateBox.getChildren().contains(templateRepoButton)) templateBox.getChildren().add(templateRepoButton);
        } else {
            templateLabel.setText("Template File");
            templateBox.getChildren().remove(templateRepoButton);
        }
    }

    private String loadUmlccFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Template File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("UMLCC Template Files", "*.umlcc")
        );

        Scene scene = root.getScene();
        if (scene == null) return "";

        Stage stage = (Stage) scene.getWindow();
        File file = fileChooser.showOpenDialog(stage);

        if (file == null) return "";

        return file.getAbsolutePath();
    }

    private String loadTemplateRepo() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Open Template Repository");

        Scene scene = root.getScene();
        if (scene == null) return "";

        Stage stage = (Stage) scene.getWindow();
        File file = dirChooser.showDialog(stage);

        if (file == null) return "";

        return file.getAbsolutePath();
    }

    private String loadTargetRepo() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Open Target Repository");

        Scene scene = root.getScene();
        if (scene == null) return "";

        Stage stage = (Stage) scene.getWindow();
        File file = dirChooser.showDialog(stage);

        if (file == null) return "";
        return file.getAbsolutePath();
    }


}
