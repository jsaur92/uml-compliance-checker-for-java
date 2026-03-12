module com.umlcc.umlcc {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens com.umlcc.umlcc to javafx.fxml;
    exports com.umlcc.umlcc;
}