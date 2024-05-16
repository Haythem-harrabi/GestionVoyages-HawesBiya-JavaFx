package com.example.hawesbiya.voyageManagement.controller;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import com.example.hawesbiya.voyageManagement.entities.Voyage;
import com.example.hawesbiya.voyageManagement.services.ServiceVoyage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.security.Provider;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CalendarController implements Initializable {

    @FXML
    private CalendarView calendarView;

    ObservableList<Voyage> Voyages = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Calendar calendar = new Calendar("Trips");
        calendar.setReadOnly(true);
        calendar.setStyle(Calendar.Style.STYLE4);

        CalendarSource calendarSource = new CalendarSource("My Calendar Source");
        calendarSource.getCalendars().add(calendar);

        calendarView.getCalendarSources().add(calendarSource);
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowSourceTrayButton(false);





calendarView.setShowPageSwitcher(true);
calendarView.setShowPageToolBarControls(false);
calendarView.setPrefHeight(457);
calendarView.setPrefWidth(765);
calendarView.getWeekPage().setPrefHeight(457);
calendarView.getDayPage().setPrefHeight(457);
calendarView.getMonthPage().setPrefHeight(457);
calendarView.getYearPage().setPrefHeight(457);






        ZoneId zoneId = ZoneId.systemDefault();


        ServiceVoyage sv= new ServiceVoyage();
        List<Voyage> ListeVoyages = null;
        try {
            ListeVoyages = sv.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Voyages = FXCollections.observableList(ListeVoyages);
        for (Voyage v : Voyages
             ) {
            LocalDate startDate = v.getDateDep();

            ZonedDateTime startDateTime = startDate.atStartOfDay(zoneId);

            Entry entry = new Entry(v.getDepart()+" ---> "+v.getDestination());
            entry.setInterval(startDateTime);
            entry.setId(String.valueOf(v.getId()));
            calendar.addEntry(entry);
        }

        setentrypopover(sv,zoneId,calendar);


    }

    public void setentrypopover(ServiceVoyage sv ,ZoneId zoneId,Calendar calendar){
        // Customize the popover for each entry
        calendarView.setEntryDetailsPopOverContentCallback(param -> {
            Entry<?> entry = param.getEntry();

            // Create a VBox to hold the content
            VBox vbox = new VBox();
            vbox.getStyleClass().add("entry-details-popup");

            // Add title label
            Label titleLabel = new Label(entry.getTitle());
            titleLabel.getStyleClass().add("entry-details-title");
            vbox.getChildren().add(titleLabel);

            // Add start and end time labels
            Voyage voyage=null;
            try {
                voyage = sv.getVoyageById(Integer.parseInt(entry.getId()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            Label startDateLabel = new Label("Departure Date: " + entry.getStartDate());
            Label endTimeLabel = new Label("Estimated Arrival Date: " + voyage.getDateArr());
            Label price=new Label("Price : " + voyage.getPrix()+" TND");
            Label nbr=new Label("Available seats : " + voyage.getNombrePlaceDispo());
            Label transport=new Label("Transportation : " + voyage.getMoyenTransport());
            Label hebergement=new Label("Accomodation : " + voyage.getHebergement());
            startDateLabel.getStyleClass().add("label");
            endTimeLabel.getStyleClass().add("label");
            price.getStyleClass().add("label");
            nbr.getStyleClass().add("label");
            transport.getStyleClass().add("label");
            hebergement.getStyleClass().add("label");
            HBox hbox=new HBox();
            Button updateButton = new Button("Update");
            updateButton.getStyleClass().add("updatebutton");
            updateButton.setOnAction(event -> {
                Voyage v=null;
                try {
                    v=sv.getVoyageById(Integer.parseInt(entry.getId()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hawesbiya/voyageManagement/UpdateVoyage.fxml"));
                try {
                    Parent root = loader.load();
                    this.getCalendarView().getScene().setRoot(root);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

                UpdateVoyage updateVoyageController = loader.getController();
                updateVoyageController.setTextField(v.getId(), v.getDepart(),
                        v.getDestination(), v.getDateDep(), v.getDateArr(), String.valueOf(v.getPrix()), String.valueOf(v.getNombrePlaceDispo()), v.getMoyenTransport(), v.getHebergement(), v.getDescription());
            });

            Button DeleteButton = new Button("Delete");
            DeleteButton.getStyleClass().add("delbutton");
            DeleteButton.setOnAction(event -> {
                Voyage v=null;
                try {
                    v=sv.getVoyageById(Integer.parseInt(entry.getId()));
                    sv.delete(v);
                    this.refreshCalendar(sv,zoneId,calendar);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }









            });

            hbox.getChildren().addAll(updateButton,DeleteButton);
            hbox.setSpacing(5);
            hbox.setAlignment(Pos.CENTER);
            vbox.getChildren().addAll(startDateLabel, endTimeLabel,price,nbr,transport,hebergement,hbox);

            // Return the VBox as the custom popover content
            return vbox;
        });
    }



    private void refreshCalendar(ServiceVoyage sv, ZoneId zoneId, Calendar calendar) {
        calendar.clear();
        List<Voyage> ListeVoyages = null;
        try {
            ListeVoyages = sv.afficher();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Voyages = FXCollections.observableList(ListeVoyages);
        for (Voyage v : Voyages
        ) {
            LocalDate startDate = v.getDateDep();
            ZonedDateTime startDateTime = startDate.atStartOfDay(zoneId);
            Entry entry = new Entry(v.getDepart()+" ---> "+v.getDestination());
            entry.setInterval(startDateTime);
            entry.setId(String.valueOf(v.getId()));
            calendar.addEntry(entry);
        }
        setentrypopover(sv,zoneId,calendar);
    }


    public CalendarView getCalendarView() {
        return calendarView;
    }

    public void setCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
    }
}
