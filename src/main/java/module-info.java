module com.umlcc.umlcc {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.umlcc.umlcc to javafx.fxml;
    exports com.umlcc.umlcc;
}