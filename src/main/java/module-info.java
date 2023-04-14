module com.example.hospitalmanagementsystem_firstevaluation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens controllers to javafx.fxml;
    opens datamodel to javafx.fxml;

    exports controllers;
    exports datamodel;
}