package com.example.hawesbiya.voyageManagement.controller;

import com.example.hawesbiya.voyageManagement.entities.MoyenTransport;
import com.example.hawesbiya.voyageManagement.services.ServiceMoyen;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TrackFlightController implements Initializable {

    @FXML
    private Button cancelbtn;

    @FXML
    private TabPane tabPane;

    @FXML
    private WebView webview;

    private int MoyId;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.getSelectionModel().select(1);
    }


    @FXML
    void CancelAction(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hawesbiya/voyageManagement/VoyagesAdmin.fxml"));
            Parent root = loader.load();
            this.webview.getScene().setRoot(root);
            VoyagesAdmin VoyageAdminController= loader.getController();
            VoyageAdminController.getTabPane().getSelectionModel().select(1);
        } catch (IOException ex) {
            System.out.println(ex.getMessage()  );      }

    }

    @FXML
    void loadTravels() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hawesbiya/voyageManagement/VoyagesAdmin.fxml"));
            Parent root = loader.load();
            this.cancelbtn.getScene().setRoot(root);
            VoyagesAdmin VoyageAdminController= loader.getController();
            VoyageAdminController.loadTravels();
        } catch (IOException ex) {
            System.out.println(ex.getMessage()  );      }
    }

    public int getMoyId() {
        return MoyId;
    }

    public void setMoyId(int moyId) {
        MoyId = moyId;
    }

    public void setinfos() {
        ServiceMoyen sm=new ServiceMoyen();
        try {
       MoyenTransport m =sm.getMoyenById(MoyId);
       String model = m.getIdModele();
       webview.getEngine().load("https://fr.flightaware.com/live/flight/"+model);

            // Use JavaScript to hide all elements except the two divs
            String script = "var elements = document.querySelectorAll('body > :not(.flightPageSummaryContainer)');" +
                    "for(var i=0; i<elements.length; i++) {" +
                    "    elements[i].style.display = 'none';" +
                    "}";
            webview.getEngine().executeScript(script);
        }
        catch (SQLException e){
            e.printStackTrace();
        }



    }
}
