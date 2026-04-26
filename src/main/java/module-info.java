module com.umlcc.controller {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires java.datatransfer;
    requires java.desktop;

    opens com.umlcc.controller to javafx.fxml;
    exports com.umlcc.controller;
}