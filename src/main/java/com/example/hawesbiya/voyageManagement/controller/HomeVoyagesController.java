package com.example.hawesbiya.voyageManagement.controller;

import com.example.hawesbiya.voyageManagement.entities.Voyage;
import com.example.hawesbiya.voyageManagement.services.ServiceVoyage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class HomeVoyagesController implements Initializable {

    @FXML
    private GridPane TravelsGrid;

    @FXML
    private TextField departure;

    @FXML
    private DatePicker departuredate;

    @FXML
    private TextField destination;

    @FXML
    private TextField price;

    @FXML
    private TabPane tabPane;

    @FXML
    private Label priceError;

    ObservableList<Voyage> Voyages = FXCollections.observableArrayList();


    @FXML
    void FilterTravelsDate(ActionEvent event) {
        String dep = departure.getText();
        String des = destination.getText();
        LocalDate datedep = departuredate.getValue();
        String prix = price.getText();
        if (!prix.isEmpty() && !isConvertibleToFloat(prix)) {
            priceError.setText("Price must be a float value! ");
        } else {
            try {
                Voyages.clear();
                TravelsGrid.getChildren().clear();
                ServiceVoyage sv = new ServiceVoyage();
                List<Voyage> ListeVoyages = sv.FilterVoyages(dep, des, datedep, prix);
                Voyages = FXCollections.observableList(ListeVoyages);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            int column = 0;
            int row = 1;
            try {
                for (Voyage v : Voyages) {
                    FXMLLoader fxmlloader = new FXMLLoader();
                    fxmlloader.setLocation(getClass().getResource("/com/example/hawesbiya/voyageManagement/Front-Voyage.fxml"));

                    Pane pane = fxmlloader.load();
                    FrontVoyageController VoyController = fxmlloader.getController();
                    VoyController.setData(v);
                    VoyController.setVoyId(v.getId());


                    if (column == 2) {
                        column = 0;
                        ++row;
                    }
                    this.TravelsGrid.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(20));
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }


    @FXML
    void FilterTravels(KeyEvent event) {


        String dep = departure.getText();
        String des = destination.getText();
        LocalDate datedep = departuredate.getValue();
        String prix = price.getText();
        if (!prix.isEmpty() && !isConvertibleToFloat(prix)) {
            priceError.setText("Price must be a float value! ");
        }
      else {
            try {
                priceError.setText(null);
                Voyages.clear();
                TravelsGrid.getChildren().clear();
                ServiceVoyage sv = new ServiceVoyage();
                List<Voyage> ListeVoyages = sv.FilterVoyages(dep, des, datedep, prix);
                Voyages = FXCollections.observableList(ListeVoyages);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            int column = 0;
            int row = 1;
            try {
                for (Voyage v : Voyages) {
                    FXMLLoader fxmlloader = new FXMLLoader();
                    fxmlloader.setLocation(getClass().getResource("/com/example/hawesbiya/voyageManagement/Front-Voyage.fxml"));

                    Pane pane = fxmlloader.load();
                    FrontVoyageController VoyController = fxmlloader.getController();
                    VoyController.setData(v);
                    VoyController.setVoyId(v.getId());


                    if (column == 2) {
                        column = 0;
                        ++row;
                    }
                    this.TravelsGrid.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(20));
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }



    private boolean isConvertibleToFloat(String prix) {
        try {
            Float.parseFloat(prix);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTravels();
    }

    @FXML
    void loadTravels(){
        ServiceVoyage sv = new ServiceVoyage();
        refreshTable(sv);
    }

    void refreshTable(ServiceVoyage sv) {
        try {
            Voyages.clear();
            TravelsGrid.getChildren().clear();
            List<Voyage> ListeVoyages=sv.afficher();
            Voyages=FXCollections.observableList(ListeVoyages);
            System.out.println("refreshed");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int column=0;
        int row=1;
        try{
            for (Voyage v : Voyages){
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("/com/example/hawesbiya/voyageManagement/Front-Voyage.fxml"));

                Pane pane=fxmlloader.load();
                FrontVoyageController VoyController=fxmlloader.getController();
                VoyController.setData(v);
                VoyController.setHomeController(this);
                VoyController.setVoyId(v.getId());
                // Add event handler to the pane
                pane.setOnMouseClicked(event -> {
//                    // Handle mouse click event here
                    System.out.println("Pane clicked for voyage ID: " + v.getId());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hawesbiya/voyageManagement/DetailsVoyage.fxml"));
                    try {


                        Parent root = loader.load();
                        DetailsVoyage DetailsController=loader.getController();
                        DetailsController.setVoyid(v.getId());
                        DetailsController.setinfos();
                        pane.getScene().setRoot(root);


                    } catch (IOException ex) {
                        System.out.println(ex.getMessage()  );      }
                });

                if (column==2){
                    column=0;
                    ++row;
                }
                this.TravelsGrid.add(pane,column++,row);
                GridPane.setMargin(pane,new Insets(20));
            }

        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void ResetVoyages(MouseEvent event) {
        departure.clear();
        destination.clear();
        departuredate.setValue(null);
        price.clear();
        ServiceVoyage sv = new ServiceVoyage();
        refreshTable(sv);
    }


    public GridPane getTravelsGrid() {
        return TravelsGrid;
    }

    public void setTravelsGrid(GridPane travelsGrid) {
        TravelsGrid = travelsGrid;
    }
}
