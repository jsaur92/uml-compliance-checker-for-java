module com.umlcc.controller {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens com.umlcc.controller to javafx.fxml;
    exports com.umlcc.controller;
}