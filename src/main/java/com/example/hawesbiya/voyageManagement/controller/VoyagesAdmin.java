package com.example.hawesbiya.voyageManagement.controller;

import com.example.hawesbiya.voyageManagement.entities.MoyenTransport;
import com.example.hawesbiya.voyageManagement.entities.Voyage;
import com.example.hawesbiya.voyageManagement.services.ServiceMoyen;
import com.example.hawesbiya.voyageManagement.services.ServiceVoyage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VoyagesAdmin implements Initializable {




    @FXML
    private GridPane TravelsGrid;

    @FXML
    private GridPane MoyensGrid;


    @FXML
    private Pane TransportationPane;

    @FXML
    private Pane TravelPane;


    @FXML
    private TabPane tabPane;

   ObservableList<Voyage> Voyages = FXCollections.observableArrayList();
    ObservableList<MoyenTransport> Moyens = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    loadTravels();

    }

    @FXML
    void loadTravels(){
        ServiceVoyage sv = new ServiceVoyage();
        refreshTable(sv);
    }

    @FXML
    void loadMoyens(){
        ServiceMoyen sm = new ServiceMoyen();
        refreshTableMoyens(sm);
    }

         void refreshTable(ServiceVoyage sv) {
        try {
            Voyages.clear();
            TravelsGrid.getChildren().clear();
            List <Voyage> ListeVoyages=sv.afficher();
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
                fxmlloader.setLocation(getClass().getResource("/com/example/hawesbiya/voyageManagement/Voyage.fxml"));

                Pane pane=fxmlloader.load();
                VoyageController VoyController=fxmlloader.getController();
                VoyController.setVoyageAdminController(this);
                VoyController.setData(v);


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

    void refreshTableMoyens(ServiceMoyen sm) {
        try {
            Moyens.clear();
            MoyensGrid.getChildren().clear();
            List <MoyenTransport> ListeMoyens=sm.afficher();
            Moyens=FXCollections.observableList(ListeMoyens);
            System.out.println("transportation refreshed");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int column=0;
        int row=1;
        try{
            for (MoyenTransport m : Moyens){
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("/com/example/hawesbiya/voyageManagement/Moyen.fxml"));

                Pane pane=fxmlloader.load();
                MoyenController MoyController=fxmlloader.getController();
                MoyController.setVoyageAdminController(this);
                MoyController.setData(m);


                if (column==2){
                    column=0;
                    ++row;
                }
                this.MoyensGrid.add(pane,column++,row);
                GridPane.setMargin(pane,new Insets(20));
            }

        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void getAddView(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hawesbiya/voyageManagement/AddVoyage.fxml"));
            Parent root = loader.load();
            TravelsGrid.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage()  );      }

    }


    @FXML
    void getAddMoyenView(MouseEvent event) {



        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hawesbiya/voyageManagement/AddMoyen.fxml"));
            Parent root = loader.load();
            MoyensGrid.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage()  );      }


    }


    public GridPane getTravelsGrid() {
        return TravelsGrid;
    }

    public void setTravelsGrid(GridPane travelsGrid) {
        TravelsGrid = travelsGrid;
    }


    public GridPane getMoyensGrid() {
        return MoyensGrid;
    }

    public void setMoyensGrid(GridPane moyensGrid) {
        MoyensGrid = moyensGrid;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }
}
