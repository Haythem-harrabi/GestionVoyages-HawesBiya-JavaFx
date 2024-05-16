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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateMoyen implements Initializable {



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

        String type= Typecomb.getValue();
        String model=modele.getText();
        if (type.isEmpty()||model.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
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

        }
        else {

            Updatemoyen();



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
            String cat=null;
            if (Typecomb.getValue()=="Bus"){
                cat="Land";
            }
            else {
                cat="Air";
            }
            MoyenTransport m = new MoyenTransport(MoyenId,cat,Typecomb.getValue(),modele.getText());
            if (sv.ExistMoyen(m)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("This transportation already exists");
                alert.showAndWait();
            }
            else{
                sv.update(m);
                cleanMoyens();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Transportation updated successfully !");
                alert.showAndWait();}

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void cleanMoyens() {
        Typecomb.setValue(null);
        modele.setText(null);
    }

    void setTextField(int id, String cat, String type, String model) {

        MoyenId=id;
        Typecomb.setValue(type);
        modele.setText(model);


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
