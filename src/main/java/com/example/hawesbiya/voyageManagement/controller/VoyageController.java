package com.example.hawesbiya.voyageManagement.controller;

import com.example.hawesbiya.voyageManagement.entities.Voyage;
import com.example.hawesbiya.voyageManagement.services.ServiceVoyage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;

public class VoyageController {

    @FXML
    private Label date;



    @FXML
    private Label name;

    @FXML
    private Label price;

    @FXML
    private Pane IconsPane;

    @FXML
    private Pane containerPane;


    private int voyId;

    private VoyagesAdmin voyageAdminController;



    public void setData(Voyage voy) {
        if(getClass().getResource("/com/example/hawesbiya/voyageManagement/assets/images/" + voy.getDestination() + "/voyage.jpeg")!=null) {
            Image image = new Image(getClass().getResource("/com/example/hawesbiya/voyageManagement/assets/images/" + voy.getDestination() + "/voyage.jpeg").toString());
            BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);
            containerPane.setBackground(background);
        }
        else {
            Image image=new Image(getClass().getResource("/com/example/hawesbiya/voyageManagement/assets/images/default/voyage.jpeg").toString());
            BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);
            containerPane.setBackground(background);
        }
        name.setText(voy.getDestination());
        price.setText(voy.getPrix() + " TND");
        date.setText(String.valueOf(voy.getDateDep()));
        this.voyId = voy.getId();


        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
        deleteIcon.setStyle("-fx-cursor: hand; -glyph-size: 22px; ");
        editIcon.setStyle("-fx-cursor: hand; -glyph-size: 22px;");
        deleteIcon.setFill(Color.RED);
        editIcon.setFill(Color.GREEN);
        deleteIcon.setCursor(Cursor.HAND);
        editIcon.setCursor(Cursor.HAND);

        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

            try {
                ServiceVoyage sv = new ServiceVoyage();
                Voyage Currentvoyage = voy;
                sv.delete(Currentvoyage);
                this.voyageAdminController.refreshTable(sv);

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }


        });
        editIcon.setOnMouseClicked((MouseEvent event) -> {

            Voyage voyage = voy;
//
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hawesbiya/voyageManagement/UpdateVoyage.fxml"));

            try {
                Parent root = loader.load();
                voyageAdminController.getTravelsGrid().getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            UpdateVoyage updateVoyageController = loader.getController();
            updateVoyageController.setTextField(voyage.getId(), voyage.getDepart(),
                    voyage.getDestination(), voyage.getDateDep(), voyage.getDateArr(), String.valueOf(voyage.getPrix()), String.valueOf(voyage.getNombrePlaceDispo()), voyage.getMoyenTransport(), voyage.getHebergement(), voyage.getDescription());
//


        });

        HBox icons = new HBox(deleteIcon, editIcon);

        icons.setSpacing(10); // Set spacing between icons
        icons.setAlignment(Pos.BASELINE_RIGHT);
        // Set HBox to grow horizontally
        HBox.setHgrow(icons, Priority.ALWAYS);


        IconsPane.getChildren().add(icons);
    }

    public int getVoyId() {
        return voyId;
    }

    public void setVoyId(int voyId) {
        this.voyId = voyId;
    }


    public  void setVoyageAdminController(VoyagesAdmin voyageAdminController) {
        this.voyageAdminController = voyageAdminController;
    }
}