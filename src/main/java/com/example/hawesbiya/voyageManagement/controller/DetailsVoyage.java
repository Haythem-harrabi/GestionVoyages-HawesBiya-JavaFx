package com.example.hawesbiya.voyageManagement.controller;

import com.example.hawesbiya.voyageManagement.entities.Voyage;
import com.example.hawesbiya.voyageManagement.services.ServiceVoyage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DetailsVoyage implements Initializable {


    @FXML
    private ImageView VoyageImage;

    @FXML
    private Label arrdate;

    @FXML
    private Button book;

    @FXML
    private Label depart;

    @FXML
    private Label depdate;

    @FXML
    private Label destination;

    @FXML
    private Label hebergement;

    @FXML
    private Label places;

    @FXML
    private Label prix;

    @FXML
    private TabPane tabPane;

    @FXML
    private Label transport;

    @FXML
    private Label description;


    private int Voyid;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void setinfos(){
        ServiceVoyage sv= new ServiceVoyage();
        try {
            Voyage v =sv.getVoyageById(Voyid);
            if (getClass().getResource("/com/example/hawesbiya/voyageManagement/assets/images/" + v.getDestination() + "/voyage.jpeg") != null) {
                Image image = new Image(getClass().getResource("/com/example/hawesbiya/voyageManagement/assets/images/" + v.getDestination() + "/voyage.jpeg").toString());
                VoyageImage.setImage(image);
            } else {
                Image image = new Image(getClass().getResource("/com/example/hawesbiya/voyageManagement/assets/images/default/voyage.jpeg").toString());
                VoyageImage.setImage(image);
            }
            depart.setText(v.getDepart());
            destination.setText(v.getDestination());
            depdate.setText(String.valueOf(v.getDateDep()));
            arrdate.setText(String.valueOf(v.getDateArr()));
            prix.setText(String.valueOf(v.getPrix()) + " TND");
            places.setText(String.valueOf(v.getNombrePlaceDispo()));
            transport.setText(v.getMoyenTransport().toString());
            hebergement.setText(v.getHebergement().toString());
            description.setText(v.getDescription());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Reserver(ActionEvent event) {

    }

    @FXML
    void GoBack(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hawesbiya/voyageManagement/Home-Voyages.fxml"));
            Parent root = loader.load();
            book.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage()  );      }
    }


    public int getVoyid() {
        return Voyid;
    }

    public void setVoyid(int voyid) {
        Voyid = voyid;
    }


}
