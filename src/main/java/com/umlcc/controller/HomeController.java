package com.umlcc.controller;

import com.umlcc.model.ComplianceCheckerApplication;
import com.umlcc.model.Directory;
import com.umlcc.model.UserType;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class HomeController {
    @FXML private VBox root;
    @FXML private HBox templateBox;
    @FXML private Label templateLabel;
    @FXML private TextField templateText;
    @FXML private Button templateRepoButton;
    @FXML private Button templateRemoteGitButton;
    @FXML private TextField targetText;
    @FXML private TextArea outputText;

    private boolean adminView = false;
    private ComplianceCheckerApplication app;

    @FXML
    public void initialize() {
        app = ComplianceCheckerApplication.getInstance();
        adminView = app.getUserType() != UserType.BASIC;
        updateLayout();
        Platform.runLater( () -> root.requestFocus() );
    }

    @FXML
    protected void onSettingsClick() throws IOException {
        UmlccApplication.changeScene("config");
    }

    @FXML
    protected void onTemplateFileClick() {
        String path = loadUmlccFile();
        templateText.setText(path);
    }

    @FXML
    protected void onTemplateRepoClick() {
        String path = loadTemplateRepo();
        if (!path.isEmpty()) templateText.setText(path);
    }

    @FXML
    protected void onTemplateGitClick() {
        openGitPopup(() -> {
            File f = new File(GitPopupController.getPath());
            if (!GitPopupController.getPath().isEmpty() && f.exists() && f.isDirectory()) {
                app.loadUmlDataByRepo(GitPopupController.getPath());
                updateLayout();
            }
        });
    }

    @FXML
    protected void onTargetRepoClick() {
        String path = loadTargetRepo();
        if (!path.isEmpty()) targetText.setText(path);
    }

    @FXML
    protected void onTargetGitClick() {
        openGitPopup(() -> {
            targetText.setText(GitPopupController.getPath());
        });
    }

    @FXML
    protected void onRunCheckerClick() {
        File f = new File(getTargetPath());
        if (f.exists()) {
            if (getTemplatePath().endsWith(".umlcc")) {
                app.loadUmlDataByUmlcc(getTemplatePath());
            } else {
                app.loadUmlDataByRepo(getTemplatePath());
            }
            ArrayList<String> results = app.checkCompliance(getTargetPath());
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
        // Some users may get an error regarding X11 if they do not have
        // Code is disabled anyway so that users are not confused by the inconsistency.

//        if (System.getProperty("java.awt.headless").equals("true")) {
//            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
//            StringSelection data = new StringSelection(outputText.getText());
//            cb.setContents(data, null);
//        }
    }

    protected void updateLayout() {
        templateText.setText(app.getUmlDataPath());
        if (adminView) {
            templateLabel.setText("Template Repository / File");
            if (! templateBox.getChildren().contains(templateRepoButton)) templateBox.getChildren().add(templateRepoButton);
            if (! templateBox.getChildren().contains(templateRemoteGitButton)) templateBox.getChildren().add(templateRemoteGitButton);
        } else {
            templateLabel.setText("Template File");
            templateBox.getChildren().remove(templateRepoButton);
            templateBox.getChildren().remove(templateRemoteGitButton);
        }
    }

    private String loadUmlccFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Template File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("UMLCC Template Files", "*.umlcc")
        );
        ControllerUtil.setInitialDirectory(fileChooser, getTemplatePath());

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
        ControllerUtil.setInitialDirectory(dirChooser, getTemplatePath());

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
        ControllerUtil.setInitialDirectory(dirChooser, getTemplatePath());

        Scene scene = root.getScene();
        if (scene == null) return "";

        Stage stage = (Stage) scene.getWindow();
        File file = dirChooser.showDialog(stage);

        if (file == null) return "";
        return file.getAbsolutePath();
    }

    private void openGitPopup(Runnable onClose) {
        Stage mainStage = (Stage) root.getScene().getWindow();
        Stage popupStage = new Stage();
        popupStage.setTitle("Pull from Remote Repo");
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(mainStage);

        Scene scene = null;
        try {
            scene = new Scene(UmlccApplication.loadFXML("git_popup"), root.getWidth()*0.8, root.getHeight()*0.8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        popupStage.setScene(scene);
        // modified from https://stackoverflow.com/questions/12153622/how-to-close-a-javafx-application-on-window-close
        popupStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                onClose.run();
            }
        });
        popupStage.showAndWait();
    }

    private String getTemplatePath() {
        return templateText.getText();
    }

    private String getTargetPath() {
        return targetText.getText();
    }


}