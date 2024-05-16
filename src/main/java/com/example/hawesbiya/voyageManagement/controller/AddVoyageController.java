package com.example.hawesbiya.voyageManagement.controller;

import com.example.hawesbiya.utils.MyDB;
import com.example.hawesbiya.voyageManagement.entities.Hebergement;
import com.example.hawesbiya.voyageManagement.entities.MoyenTransport;
import com.example.hawesbiya.voyageManagement.entities.Voyage;
import com.example.hawesbiya.voyageManagement.services.ServiceMoyen;
import com.example.hawesbiya.voyageManagement.services.ServiceVoyage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.textfield.TextFields;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class AddVoyageController implements Initializable {
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


    @FXML
    private Button cancelbtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        String[] possible={"hello","bonjour"};
//        TextFields.bindAutoCompletion(destination, possible);


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


        } catch (SQLException e) {
            e.printStackTrace();
        }
        hebergementcomb.getItems().clear();
        hebergementcomb.getItems().addAll(hebergements);
    }


    private void insert() {

        try {

            ServiceVoyage sv = new ServiceVoyage();
            Voyage v = new Voyage(depart.getText(), destination.getText(), DateDep.getValue(), DateArr.getValue(), Float.parseFloat(prix.getText()), Integer.parseInt(placesdispo.getText()), transportcomb.getValue(), hebergementcomb.getValue(), description.getText());

            if (sv.ExistVoyage(v)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("This Trip already exists");
                alert.showAndWait();
            }
            else{
            sv.add(v);
                clean();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Travel added successfully !");
                alert.showAndWait();}

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @FXML
    void TakeAction(MouseEvent event) {

        String dep = depart.getText();
        String des = destination.getText();
        String datedep = String.valueOf(DateDep.getValue());
        String datearr = String.valueOf(DateArr.getValue());
        String pr = prix.getText();
        String places = placesdispo.getText();
        MoyenTransport mt = transportcomb.getValue();
        Hebergement h = hebergementcomb.getValue();
        String desc = description.getText();

        if (dep.isEmpty() || des.isEmpty() || datedep.isEmpty() || datearr.isEmpty() || pr.isEmpty() || places.isEmpty() || mt == null || h == null || desc.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        } else if (dep==des) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Departure and destination must have different values");
            alert.showAndWait();
        }else if (DateDep.getValue().isAfter(DateArr.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Departure date can't be after Arrival Date");
            alert.showAndWait();
        } else if (DateDep.getValue().isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Departure date can't be before today");
            alert.showAndWait();
        } else if (!isConvertibleToFloat(pr)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Price must be a float value");
            alert.showAndWait();
        } else if (!isConvertibleToInteger(places)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Available seats must be an int value");
            alert.showAndWait();
        } else {

            insert();



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


    @FXML
    void CancelAction(MouseEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hawesbiya/voyageManagement/VoyagesAdmin.fxml"));
            Parent root = loader.load();
            this.depart.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }


    @FXML
    void loadMoyens() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hawesbiya/voyageManagement/VoyagesAdmin.fxml"));
            Parent root = loader.load();
            this.description.getScene().setRoot(root);
            VoyagesAdmin VoyageAdminController = loader.getController();
            VoyageAdminController.loadMoyens();
            VoyageAdminController.getTabPane().getSelectionModel().select(1);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @FXML
    void AutoCompleteField(KeyEvent event) throws IOException {
        if (event.getSource() instanceof TextField) {
            TextField textField = (TextField) event.getSource();
            String value = textField.getText();
            if (value.length() >= 3) {
                String[] countryNames = GetCountries(value);
                String[] states = GetStates(value);

                if (countryNames!=null && states!=null){
                String[] combinedArray = Stream.concat(Arrays.stream(countryNames), Arrays.stream(states))
                        .toArray(String[]::new);

                TextFields.bindAutoCompletion(textField, combinedArray);
                }
            }

        }
    }


    public String[] GetCountries(String value) throws IOException{
        String[] countryNames =null;

            URL url = new URL("https://api.geoapify.com/v1/geocode/autocomplete?text=" + value + "&type=country&limit=10&apiKey=545235fb96314e3e812d8d8a04863480");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestProperty("Accept", "application/json");

            int statusCode = http.getResponseCode();
            String message = http.getResponseMessage();

            System.out.println(statusCode + " " + message);

            if (statusCode == 200) { // Check for successful response (code 200)
                BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();


                // Parse the JSON response to extract desired data (place this in a separate function)
                // You can use libraries like GSON or Jackson for JSON parsing
                if (! response.isEmpty()) {
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    JSONArray features = jsonResponse.getJSONArray("features");

                    countryNames = new String[features.length()];
                    for (int i = 0; i < features.length(); i++) {
                        JSONObject feature = features.getJSONObject(i);
                        JSONObject properties = feature.getJSONObject("properties");
                        if (properties.has("name")){
                        String countryName = properties.getString("name");
                        countryNames[i] = countryName;}
                    }
                }

            } else {
                System.out.println("Error: " + message);
            }

            http.disconnect();

        return countryNames;


    }


    public String[] GetStates(String value) throws IOException{
        String[] states =null;

        URL url = new URL("https://api.geoapify.com/v1/geocode/autocomplete?text=" + value + "&type=state&limit=10&apiKey=545235fb96314e3e812d8d8a04863480");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("Accept", "application/json");

        int statusCode = http.getResponseCode();
        String message = http.getResponseMessage();

        System.out.println(statusCode + " " + message);

        if (statusCode == 200) { // Check for successful response (code 200)
            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();


            // Parse the JSON response to extract desired data (place this in a separate function)
            // You can use libraries like GSON or Jackson for JSON parsing
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray features = jsonResponse.getJSONArray("features");

             states = new String[features.length()];
            for (int i = 0; i < features.length(); i++) {
                JSONObject feature = features.getJSONObject(i);
                JSONObject properties = feature.getJSONObject("properties");
                if (properties.has("state")){
                String state = properties.getString("state");
                states[i] = state;}
            }


        } else {
            System.out.println("Error: " + message);
        }

        http.disconnect();

        return states;


    }



}
