package com.umlcc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ConfigController {

    @FXML
    private VBox root;

    @FXML
    private TextField targetText;

    @FXML
    void onApplyClick(MouseEvent event) {

    }

    @FXML
    void onCancelClick(MouseEvent event) throws IOException {
        UmlccApplication.changeScene("home");
    }

    @FXML
    void onOkayClick(MouseEvent event) throws IOException {
        UmlccApplication.changeScene("home");
    }

    @FXML
    void onTargetRepoClick(MouseEvent event) {

    }

    @FXML
    void onTemplateFileClick(MouseEvent event) {

    }

    @FXML
    void onUmlClick(MouseEvent event) {

    }

}
