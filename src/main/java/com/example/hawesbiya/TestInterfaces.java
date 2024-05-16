package com.example.hawesbiya;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.hawesbiya.voyageManagement.controller.AddVoyageController;

import java.util.Locale;

public class TestInterfaces extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("voyageManagement/VoyagesAdmin.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("HawesBiya App");
        primaryStage.setScene(new Scene(root, 1000, 562));
        primaryStage.show();
    }
}