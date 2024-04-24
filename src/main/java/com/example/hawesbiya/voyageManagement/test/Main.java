package com.example.hawesbiya.voyageManagement.test;


import com.example.hawesbiya.voyageManagement.entities.Hebergement;

import com.example.hawesbiya.voyageManagement.entities.MoyenTransport ;
import com.example.hawesbiya.voyageManagement.entities.Voyage;
import com.example.hawesbiya.voyageManagement.services.ServiceVoyage;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        Voyage p1 = new Voyage(6,"depart", "destination", LocalDate.now(), LocalDate.now().plusDays(1),
                LocalTime.now(), LocalTime.now().plusHours(1), 100.0f, 50, new MoyenTransport(), new Hebergement(), "description");



        ServiceVoyage sp = new ServiceVoyage();

        try {

            System.out.println(sp.afficher());

        }catch (Exception e){
            System.out.println( e.getMessage());
        }
    }
}