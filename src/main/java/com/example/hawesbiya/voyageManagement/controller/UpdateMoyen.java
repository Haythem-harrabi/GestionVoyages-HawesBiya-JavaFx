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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateMoyen implements Initializable {

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

    int MoyenId;



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
        }
        else {

            Updatemoyen();
            cleanMoyens();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Transportation updated successfully !");
            alert.showAndWait();


        }

    }

    @FXML
    void loadTravels(){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hawesbiya/voyageManagement/VoyagesAdmin.fxml"));
            Parent root = loader.load();
            this.modele.getScene().setRoot(root);
            VoyagesAdmin VoyageAdminController= loader.getController();
            VoyageAdminController.loadTravels();
        } catch (IOException ex) {
            System.out.println(ex.getMessage()  );      }
    }

    private void Updatemoyen() {
        try {

            ServiceMoyen sv = new ServiceMoyen();
            MoyenTransport m = new MoyenTransport(MoyenId,Categorycomb.getValue(),Typecomb.getValue(),modele.getText());
            sv.update(m);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void cleanMoyens() {
        Categorycomb.setValue(null);
        Typecomb.setValue(null);
        modele.setText(null);
    }

    void setTextField(int id, String cat, String type, String model) {

        MoyenId=id;
        Categorycomb.setValue(cat);
        Typecomb.setValue(type);
        modele.setText(model);


    }

}
