package com.umlcc.controller;

import com.umlcc.model.ComplianceCheckerApplication;
import javafx.application.Platform;
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

    @FXML
    private void initialize() {
        path = "";
        Platform.runLater( () -> root.requestFocus() );
    }

    /**
     * Attempts to clone and pull the desired remote Git repo.
     * In the future, this should be altered to provide displayed output
     * into a textfield in the popup much like the output that would be
     * displayed on a terminal window. It should also try to intelligently
     * decide whether to use clone or pull based on context.
     */
    @FXML
    private void onClonePullClick(MouseEvent event) {
        String[] commandClone = {
                "git",
                "clone",
                targetText.getText(),
                templateText.getText()
        };
        String[] commandPull = {
                "git",
                "-C",
                templateText.getText(),
                "pull"
        };

        try {
            Process procClone = Runtime.getRuntime().exec(commandClone);
            String cloneOut = getCommandOutput(procClone);

            outputText.appendText("Attempting to clone.\n");
            outputText.appendText(cloneOut);
            if (!cloneOut.startsWith("fatal:")) {
                outputText.appendText("Clone successful.\n");
            } else {
                outputText.appendText("Clone failed. Attempting to pull.\n");
                Process procPull = Runtime.getRuntime().exec(commandPull);
                String pullOut = getCommandOutput(procPull);
                outputText.appendText(pullOut);
                if (!pullOut.startsWith("fatal:")) {
                    outputText.appendText("Pull successful.\n");
                } else {
                    outputText.appendText("Pull failed.\n");
                    return;
                }
            }

            path = templateText.getText();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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

    // modified from https://stackoverflow.com/questions/5711084/java-runtime-getruntime-getting-output-from-executing-a-command-line-program
    public String getCommandOutput(Process proc) throws IOException {
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        String s = "";

        // Read the output from the command
        String output = null;
        while ((output = stdInput.readLine()) != null) {
            s += output + "\n";
        }

        String error = null;
        // Read any errors from the attempted command
        while ((error = stdError.readLine()) != null) {
            s += error + "\n";
        }

        return s;
    }

    public static String getPath() {
        return path;
    }
}