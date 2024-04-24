package com.example.hawesbiya.voyageManagement.controller;

import com.example.hawesbiya.voyageManagement.entities.Hebergement;
import com.example.hawesbiya.voyageManagement.entities.MoyenTransport;
import com.example.hawesbiya.voyageManagement.entities.Voyage;
import com.example.hawesbiya.voyageManagement.services.ServiceMoyen;
import com.example.hawesbiya.voyageManagement.services.ServiceVoyage;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddMoyenController implements Initializable {

    @FXML
    private ComboBox<String> Categorycomb;

    @FXML
    private ComboBox<String> Typecomb;

    @FXML
    private Button cancelbtn;

    @FXML
    private TextField modele;

    @FXML
    private TabPane tabPane;




    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabPane.getSelectionModel().select(1);
        Categorycomb.setItems(FXCollections.observableArrayList("Air","Land"));
        Typecomb.setItems(FXCollections.observableArrayList("Plane","Bus"));
    }
    @FXML
    void CancelAction(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hawesbiya/voyageManagement/VoyagesAdmin.fxml"));
            Parent root = loader.load();
            this.modele.getScene().setRoot(root);
            VoyagesAdmin VoyageAdminController= loader.getController();
            VoyageAdminController.getTabPane().getSelectionModel().select(1);
        } catch (IOException ex) {
            System.out.println(ex.getMessage()  );      }

    }

   @FXML
    void loadTravels(){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hawesbiya/voyageManagement/VoyagesAdmin.fxml"));
            Parent root = loader.load();
            this.Categorycomb.getScene().setRoot(root);
            VoyagesAdmin VoyageAdminController= loader.getController();
            VoyageAdminController.loadTravels();
        } catch (IOException ex) {
            System.out.println(ex.getMessage()  );      }
    }



    @FXML
    void TakeAction(MouseEvent event) {
        String cat=Categorycomb.getValue();
        String type= Typecomb.getValue();
        String model=modele.getText();
        if (cat.isEmpty()||type.isEmpty()||model.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        } else if ((type=="Bus" && cat=="Air") || (type=="Plane" && cat=="Land") ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Category and type fields are not compatible !");
            alert.showAndWait();
        } else if (! ModelisValid()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            if (Typecomb.getValue()=="Bus"){
            alert.setContentText("Invalid model : must be [2-3 digits][3 letters][2-4 digits] !");}
            else {
                alert.setContentText("Invalid model : must be [3-4 letters][2-4 digits] !");
            }
            alert.showAndWait();

        } else {
            insertMoyen();


        }
    }

    private void cleanMoyens() {
        Categorycomb.setValue(null);
        Typecomb.setValue(null);
        modele.setText(null);
    }

    private void insertMoyen() {
        try {

            ServiceMoyen sv = new ServiceMoyen();
            MoyenTransport m = new MoyenTransport(Categorycomb.getValue(),Typecomb.getValue(),modele.getText());
            if (sv.ExistMoyen(m)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("This transportation already exists");
                alert.showAndWait();
            }
            else{
                 sv.add(m);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Transportation added successfully !");
                alert.showAndWait();
                cleanMoyens();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    private boolean ModelisValid() {
        String busRegex = "^\\d{2,3}[A-Za-z]{3}\\d{2,4}$";
        String PlaneRegex = "^[A-Za-z]{3,4}\\d{2,4}$";
        Pattern pattern= null;
        if (Typecomb.getValue()=="Bus"){
             pattern = Pattern.compile(busRegex);
        }
        else {
             pattern = Pattern.compile(PlaneRegex);
        }
        Matcher matcher = pattern.matcher(modele.getText());
        if(matcher.matches()){
            return true;
        }
        return false;
    }

}
