package com.umlcc.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HomeController {
    @FXML
    private Button openLocalTemplate;
    @FXML
    private Button openLocalTarget;
    @FXML
    private Button cloneTarget;
    @FXML
    private Button generateUmlccFile;
    @FXML
    private Label templateLabel;

    final private double basicViewOpenLocalY = 161;
    final private double adminViewOpenLocalY = 127;
    private boolean adminView = true;

    @FXML
    protected void onOpenLocalTemplateRepoClick() {
        System.out.println("Open Local Template Repo");
    }

    @FXML
    protected void onOpenUmlccFileClick() {
        System.out.println("Open .umlcc file");
    }

    @FXML
    protected void onOpenLocalTargetRepoClick() {
        System.out.println("Open Local Target Repo");
    }

    @FXML
    protected void onCloneTargetRepoClick() {
        System.out.println("Clone and Open Template Repo");
    }

    @FXML
    protected void onGenerateUmlccClick() {
        System.out.println("Generate .umlcc File");
    }

    @FXML
    protected void onRunCheckerClick() {
        System.out.println("Run Compliance Checker");
    }

    @FXML
    protected void onSettingsClick() {
        System.out.println("Open Settings");
    }

    @FXML
    protected void toggleView() {
        if (adminView)
            setBasicView();
        else
            setAdminView();
        adminView = !adminView;
    }

    protected void setBasicView() {
        openLocalTemplate.setVisible(false);
        cloneTarget.setVisible(false);
        generateUmlccFile.setVisible(false);
        openLocalTarget.setLayoutY(basicViewOpenLocalY);
        templateLabel.setText("Template File");
    }

    protected void setAdminView() {
        openLocalTemplate.setVisible(true);
        cloneTarget.setVisible(true);
        generateUmlccFile.setVisible(true);
        openLocalTarget.setLayoutY(adminViewOpenLocalY);
        templateLabel.setText("Template Repository / File");
    }
}
