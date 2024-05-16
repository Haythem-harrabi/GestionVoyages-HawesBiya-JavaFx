package com.example.hawesbiya.voyageManagement.controller;

import com.example.hawesbiya.voyageManagement.entities.Voyage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;

public class FrontVoyageController {

    @FXML
    private AnchorPane containerPane;

    @FXML
    private Label date;

    @FXML
    private Label depart;

    @FXML
    private Label name;

    @FXML
    private Label price;

    private int voyId;

    private HomeVoyagesController homeController;


    public void setData(Voyage voy) {
        if (getClass().getResource("/com/example/hawesbiya/voyageManagement/assets/images/" + voy.getDestination() + "/voyage.jpeg") != null) {
            Image image = new Image(getClass().getResource("/com/example/hawesbiya/voyageManagement/assets/images/" + voy.getDestination() + "/voyage.jpeg").toString());
            BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);
            containerPane.setBackground(background);
        } else {
            Image image = new Image(getClass().getResource("/com/example/hawesbiya/voyageManagement/assets/images/default/voyage.jpeg").toString());
            BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);
            containerPane.setBackground(background);
        }
        name.setText(voy.getDestination());
        depart.setText(voy.getDepart());
        price.setText(voy.getPrix() + " TND");
        date.setText(String.valueOf(voy.getDateDep()));
        this.voyId = voy.getId();
    }

    public int getVoyId() {
        return voyId;
    }

    public void setVoyId(int voyId) {
        this.voyId = voyId;
    }




    public HomeVoyagesController getHomeController() {
        return homeController;
    }

    public void setHomeController(HomeVoyagesController homeController) {
        this.homeController = homeController;
    }



}
