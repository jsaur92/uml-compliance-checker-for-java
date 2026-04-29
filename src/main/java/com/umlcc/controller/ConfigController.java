package com.umlcc.controller;

import com.umlcc.model.CloneIntoPattern;
import com.umlcc.model.ComplianceCheckerApplication;
import com.umlcc.model.UserType;
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
    @FXML private CheckBox deleteCheckBox;
    @FXML private GridPane complianceGridPane;
    @FXML private VBox complianceVBox;
    @FXML private AnchorPane complianceAnchorPane;
    @FXML private HBox topHBox;
    @FXML private HBox generateUmlccBox;

    // default width of the root - default scrollbar width + scrollbar panel padding
    // (adjusted a little lower to account for the scrollbar itself)
    private final double compWidthMargin = 36;
    private final String hideGUBUser = "Basic";
    private ArrayList<ComplianceGridRow> gridRows = new ArrayList<ComplianceGridRow>();
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
        updateAll();
    }

    @FXML
    private void onApplyClick(MouseEvent event) {
        saveSettings();
    }

    @FXML
    private void onCancelClick(MouseEvent event) throws IOException {
        UmlccApplication.changeScene("home");
    }

    @FXML
    private void onOkayClick(MouseEvent event) throws IOException {
        saveSettings();
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
        checkAll(true);
    }

    @FXML
    private void onUncheckAllClick(MouseEvent event) {
        checkAll(false);
    }

    private void checkAll(boolean check) {
        for (ComplianceGridRow gridRow : gridRows) {
            gridRow.getCheckBox().setSelected(check);
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

    private void updateAll() {
        UserType userType = app.getUserType();
        String defaultRepo = app.getDefaultCloneParent();
        CloneIntoPattern pattern = app.getCloneIntoPattern();
        boolean deleteOnClose = app.getDeleteClonedOnClose();

        userTypeMenu.setText(userType.toString());
        targetText.setText(defaultRepo);
        cloneIntoPrefixMenu.setText(pattern.toString());
        deleteCheckBox.setSelected(deleteOnClose);

        updateUmlccButtonVis();
        updateComplianceWarnings();
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
            ComplianceGridRow newRow = compWarningRow(i,
                    warning.toString(),
                    active,
                    (active)? savedWarnings.get(warning) : "" );
            gridRows.add(newRow);//add checkbox to list
            complianceGridPane.getChildren().addAll(newRow.getAll());
            i++;
        }
    }

    // returns array of all the content in a row of the compliance warnings grid.s
    private ComplianceGridRow compWarningRow(int rowIndex, String warning, boolean active, String results) {
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

        return new ComplianceGridRow(text, checkBox, field);
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

    private void saveSettings() {
        setUserPreferences();
        setConfig();
    }

    private void setUserPreferences() {
        UserType userType = UserType.fromString(userTypeMenu.getText());
        String defaultRepo = targetText.getText();
        CloneIntoPattern pattern = CloneIntoPattern.fromString(cloneIntoPrefixMenu.getText());
        boolean deleteOnClose = deleteCheckBox.isSelected();

        app.setUserType(userType);
        app.setDefaultCloneParent(defaultRepo);
        app.setCloneIntoPattern(pattern);
        app.setDeleteClonedOnClose(deleteOnClose);
    }

    private void setConfig() {
        HashMap<Warning, String> warnings = new HashMap<>();
        for (ComplianceGridRow gridRow : gridRows) {
            if (gridRow.isChecked()) {
                warnings.put(Warning.fromString(gridRow.getWarningText()),
                             gridRow.getFieldText());
            }
        }
        app.setConfig(warnings);
    }

}

// helper class that holds a row of the ComplianceWarning grid.
class ComplianceGridRow {
    private Label warningText;
    private CheckBox checkBox;
    private TextField field;

    public ComplianceGridRow(Label warningText, CheckBox checkBox, TextField field) {
        this.warningText = warningText;
        this.checkBox = checkBox;
        this.field = field;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public Control[] getAll() {
        return new Control[]{warningText, checkBox, field};
    }

    public String getWarningText() {
        return warningText.getText();
    }

    public boolean isChecked() {
        return checkBox.isSelected();
    }

    public String getFieldText() {
        return field.getText();
    }
}
