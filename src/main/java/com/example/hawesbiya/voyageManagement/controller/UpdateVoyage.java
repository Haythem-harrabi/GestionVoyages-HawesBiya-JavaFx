package com.example.hawesbiya.voyageManagement.controller;

import com.example.hawesbiya.utils.MyDB;
import com.example.hawesbiya.voyageManagement.entities.Hebergement;
import com.example.hawesbiya.voyageManagement.entities.MoyenTransport;
import com.example.hawesbiya.voyageManagement.entities.Voyage;
import com.example.hawesbiya.voyageManagement.services.ServiceMoyen;
import com.example.hawesbiya.voyageManagement.services.ServiceVoyage;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateVoyage implements Initializable {
    public Connection con;
    public Statement ste;

    @FXML
    private DatePicker DateArr;

    @FXML
    private DatePicker DateDep;

    @FXML
    private TextField depart;

    @FXML
    private TextField destination;

    @FXML
    private ComboBox<Hebergement> hebergementcomb;

    @FXML
    private TextField placesdispo;

    @FXML
    private TextField prix;

    @FXML
    private TabPane tabPane;

    @FXML
    private TextArea description;

    @FXML
    private ComboBox<MoyenTransport> transportcomb;


    int Voyageid;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceMoyen sm = new ServiceMoyen();
        List<MoyenTransport> transports = null;
        try {
            transports = sm.afficher();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        transportcomb.getItems().addAll(transports);
        SelectHebergement();
    }


    void SelectHebergement() {
        List<Hebergement> hebergements = new ArrayList<>();
        con = MyDB.getInstance().getCon();
        try (Statement statement = con.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM hebergement")) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");

                Hebergement hebergement = new Hebergement(id);
                hebergements.add(hebergement);
            }
//            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        hebergementcomb.getItems().clear();
        hebergementcomb.getItems().addAll(hebergements);
    }




    private void update() {

        try {

            ServiceVoyage sv = new ServiceVoyage();
            Voyage v = new Voyage(Voyageid,depart.getText(),destination.getText(),DateDep.getValue(),DateArr.getValue(),Float.parseFloat(prix.getText()), Integer.parseInt(placesdispo.getText()),transportcomb.getValue(),hebergementcomb.getValue(),description.getText());
            sv.update(v);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }



    @FXML
    void TakeAction(MouseEvent event) {

        String dep= depart.getText();
        String des=destination.getText();
        String datedep=String.valueOf(DateDep.getValue());
        String datearr=String.valueOf(DateArr.getValue());
        String pr= prix.getText();
        String places=placesdispo.getText();
        MoyenTransport mt=transportcomb.getValue();
        Hebergement h= hebergementcomb.getValue();
        String desc=description.getText();

        if (dep.isEmpty()||des.isEmpty()||datedep.isEmpty()||datearr.isEmpty()||pr.isEmpty()||places.isEmpty()||mt==null||h==null||desc==null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        } else if (DateDep.getValue().isAfter(DateArr.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Departure date can't be after Arrival Date");
            alert.showAndWait();
        }
        else if (DateDep.getValue().isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Departure date can't be before today");
            alert.showAndWait();
        }
        else if (! isConvertibleToFloat(pr)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Price must be a float value");
            alert.showAndWait();
        }
        else if (! isConvertibleToInteger(places)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Available seats must be an int value");
            alert.showAndWait();
        }
        else {
                update();
                clean();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Travel updated successfully !");
                alert.showAndWait();
            }
        }

    private boolean isConvertibleToInteger(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isConvertibleToFloat(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    private void clean() {
        depart.setText(null);
        destination.setText(null);
        DateDep.setValue(null);
        DateArr.setValue(null);
        prix.setText(null);
        placesdispo.setText(null);
        transportcomb.setValue(null);
        hebergementcomb.setValue(null);
        description.setText(null);
    }


    void setTextField(int id,String dep, String des, LocalDate dated,LocalDate datea, String pr, String places,MoyenTransport mt,Hebergement h,String desc) {

        Voyageid=id;
        depart.setText(dep);
        destination.setText(des);
        DateDep.setValue(dated);
        DateArr.setValue(datea);
        prix.setText(pr);
        placesdispo.setText(places);
        transportcomb.setValue(mt);
        hebergementcomb.setValue(h);
        description.setText(desc);

    }

    @FXML
    void CancelAction(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hawesbiya/voyageManagement/VoyagesAdmin.fxml"));
            Parent root = loader.load();
            this.depart.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage()  );      }

    }

    @FXML
    void loadMoyens(){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hawesbiya/voyageManagement/VoyagesAdmin.fxml"));
            Parent root = loader.load();
            this.description.getScene().setRoot(root);
            VoyagesAdmin VoyageAdminController= loader.getController();
            VoyageAdminController.loadMoyens();
            VoyageAdminController.getTabPane().getSelectionModel().select(1);
        } catch (IOException ex) {
            System.out.println(ex.getMessage()  );      }
    }
}
