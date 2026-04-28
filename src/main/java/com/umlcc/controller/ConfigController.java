package com.umlcc.controller;

import com.umlcc.model.ComplianceCheckerApplication;
import com.umlcc.model.Warning;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ConfigController {

    @FXML private VBox root;
    @FXML private TextField targetText;
    @FXML private ScrollPane complianceScrollPane;
    @FXML private MenuButton userTypeMenu;
    @FXML private MenuButton cloneIntoPrefixMenu;
    @FXML private GridPane complianceGridPane;
    @FXML private VBox complianceVBox;
    @FXML private AnchorPane complianceAnchorPane;
    @FXML private HBox topHBox;
    @FXML private HBox generateUmlccBox;

    // default width of the root - default scrollbar width + scrollbar panel padding
    // (adjusted a little lower to account for the scrollbar itself)
    private final double compWidthMargin = 36;
    private final String hideGUBUser = "Basic";
    private ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
    private ComplianceCheckerApplication app;

    @FXML
    public void initialize() {
        app = ComplianceCheckerApplication.getInstance();
        // modified from https://blog.idrsolutions.com/adding-a-window-resize-listener-to-javafx-scene/
        UmlccApplication.getScene().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                updateComplianceWidth();
            }
        });
        updateComplianceWidth();
        updateComplianceWarnings();
        updateUmlccButtonVis();
    }

    @FXML
    private void onApplyClick(MouseEvent event) {

    }

    @FXML
    private void onCancelClick(MouseEvent event) throws IOException {
        UmlccApplication.changeScene("home");
    }

    @FXML
    private void onOkayClick(MouseEvent event) throws IOException {
        UmlccApplication.changeScene("home");
    }

    @FXML
    private void onTargetRepoClick(MouseEvent event) {
        String path = loadTargetRepo();
        if (!path.isEmpty()) targetText.setText(path);
    }

    @FXML
    private void onTemplateFileClick(MouseEvent event) {

    }

    @FXML
    private void onUmlClick(MouseEvent event) {

    }

    @FXML
    private void onCheckAllClick(MouseEvent event) {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setSelected(true);
        }
    }

    @FXML
    private void onUncheckAllClick(MouseEvent event) {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setSelected(false);
        }
    }

    @FXML
    private void onUserTypeChange(ActionEvent event) {
        userTypeMenu.setText(((MenuItem)event.getSource()).getText());
        updateUmlccButtonVis();
    }

    @FXML
    private void onCloneIntoPrefixChange(ActionEvent event) {
        cloneIntoPrefixMenu.setText(((MenuItem)event.getSource()).getText());
    }

    private void updateComplianceWidth() {
        complianceAnchorPane.setPrefWidth( UmlccApplication.getScene().getWidth() - compWidthMargin );
        complianceVBox.setPrefWidth( UmlccApplication.getScene().getWidth() - compWidthMargin );
    }

    private void updateComplianceWarnings() {
        complianceGridPane.getChildren().removeAll();
        HashMap<Warning, String> savedWarnings = app.getConfig().getWarningMessages();
        int i = 0;
        for (Warning warning : Warning.values()) {
            boolean active = savedWarnings.containsKey(warning);
            Control[] newRow = compWarningRow(i,
                    warning.toString(),
                    active,
                    (active)? savedWarnings.get(warning) : "" );
            checkBoxes.add((CheckBox) newRow[1]);//add checkbox to list
            complianceGridPane.getChildren().addAll(newRow);
            i++;
        }
    }

    // returns array of all the content in a row of the compliance warnings grid.s
    private Control[] compWarningRow(int rowIndex, String warning, boolean active, String results) {
        Label text = new Label(warning);
        GridPane.setRowIndex(text, rowIndex);
        GridPane.setColumnIndex(text, 0);

        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(active);
        checkBox.setMnemonicParsing(false);
        GridPane.setRowIndex(checkBox, rowIndex);
        GridPane.setColumnIndex(checkBox, 1);
        GridPane.setMargin(checkBox, new Insets(0, 5, 0, 5));

        TextField field = new TextField(results);
        GridPane.setRowIndex(field, rowIndex);
        GridPane.setColumnIndex(field, 2);
        field.setDisable(!active);

        checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean last, Boolean current) {
                field.setDisable(!current);
            }
        });

        return new Control[]{text, checkBox, field};
    }

    private void updateUmlccButtonVis() {
        if (userTypeMenu.getText().equals(hideGUBUser)) {
            topHBox.getChildren().remove(generateUmlccBox);
        } else if (!topHBox.getChildren().contains(generateUmlccBox)) {
            topHBox.getChildren().add(generateUmlccBox);
        }
    }

    private String loadTargetRepo() {
        DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Open Target Repository");
        ControllerUtil.setInitialDirectory(dirChooser, targetText.getText());

        Scene scene = root.getScene();
        if (scene == null) return "";

        Stage stage = (Stage) scene.getWindow();
        File file = dirChooser.showDialog(stage);

        if (file == null) return "";
        return file.getAbsolutePath();
    }

}
