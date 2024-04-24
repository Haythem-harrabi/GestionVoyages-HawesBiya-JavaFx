module com.example.hawesbiya {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;


    opens com.example.hawesbiya to javafx.fxml;
    exports com.example.hawesbiya;
    exports com.example.hawesbiya.voyageManagement.controller;
    opens com.example.hawesbiya.voyageManagement.controller to javafx.fxml;
    exports com.example.hawesbiya.voyageManagement.entities;
}