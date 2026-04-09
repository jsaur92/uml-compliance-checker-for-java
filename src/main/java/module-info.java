module com.umlcc.controller {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens com.umlcc.controller to javafx.fxml;
    exports com.umlcc.controller;
}