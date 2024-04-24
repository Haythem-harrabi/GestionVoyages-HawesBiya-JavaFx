package com.example.hawesbiya.voyageManagement.entities;


import java.time.LocalDate;
import java.time.LocalTime;

public class Voyage {

    //public static int CreatedVoy=1;
    private int id;
    private String depart;
    private String destination;
    private LocalDate DateDep;
    private LocalDate DateArr;
    private LocalTime HeureDep;
    private LocalTime HeureArr;
    private Float prix;
    private int NombrePlaceDispo;
    private MoyenTransport moyenTransport;
    private Hebergement hebergement;
    private String description;


    public Voyage() {
    }

    public Voyage(String depart, String destination, LocalDate dateDep, LocalDate dateArr, Float prix, int nombrePlaceDispo, MoyenTransport moyenTransport, Hebergement hebergement) {
        this.depart = depart;
        this.destination = destination;
        DateDep = dateDep;
        DateArr = dateArr;
        this.prix = prix;
        NombrePlaceDispo = nombrePlaceDispo;
        this.moyenTransport = moyenTransport;
        this.hebergement = hebergement;
    }

    public Voyage(int id,String depart, String destination, LocalDate dateDep, LocalDate dateArr, Float prix, int nombrePlaceDispo, MoyenTransport moyenTransport, Hebergement hebergement, String description) {
        this.id=id;
        this.depart = depart;
        this.destination = destination;
        DateDep = dateDep;
        DateArr = dateArr;
        this.prix = prix;
        NombrePlaceDispo = nombrePlaceDispo;
        this.moyenTransport = moyenTransport;
        this.hebergement = hebergement;
        this.description = description;
    }

    public Voyage(String depart, String destination, LocalDate dateDep, LocalDate dateArr, Float prix, int nombrePlaceDispo, MoyenTransport moyenTransport, Hebergement hebergement, String description) {

        this.depart = depart;
        this.destination = destination;
        DateDep = dateDep;
        DateArr = dateArr;
        this.prix = prix;
        NombrePlaceDispo = nombrePlaceDispo;
        this.moyenTransport = moyenTransport;
        this.hebergement = hebergement;
        this.description = description;
    }

    public Voyage(int id, String depart, String destination, LocalDate dateDep, LocalDate dateArr, LocalTime heureDep, LocalTime heureArr, Float prix, int nombrePlaceDispo, MoyenTransport moyenTransport, Hebergement hebergement, String description) {
        this.id = id;
        this.depart = depart;
        this.destination = destination;
        this.DateDep = dateDep;
        DateArr = dateArr;
        HeureDep = heureDep;
        HeureArr = heureArr;
        this.prix = prix;
        this.NombrePlaceDispo = nombrePlaceDispo;
        this.moyenTransport = moyenTransport;
        this.hebergement = hebergement;
        this.description = description;
    }

    public Voyage(String depart, String destination, LocalDate dateDep, LocalDate dateArr, LocalTime heureDep, LocalTime heureArr, Float prix, int nombrePlaceDispo, MoyenTransport moyenTransport, Hebergement hebergement, String description) {
        this.depart = depart;
        this.destination = destination;
        DateDep = dateDep;
        DateArr = dateArr;
        HeureDep = heureDep;
        HeureArr = heureArr;
        this.prix = prix;
        NombrePlaceDispo = nombrePlaceDispo;
        this.moyenTransport = moyenTransport;
        this.hebergement = hebergement;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDateDep() {
        return DateDep;
    }

    public void setDateDep(LocalDate dateDep) {
        DateDep = dateDep;
    }

    public LocalDate getDateArr() {
        return DateArr;
    }

    public void setDateArr(LocalDate dateArr) {
        DateArr = dateArr;
    }

    public LocalTime getHeureDep() {
        return HeureDep;
    }

    public void setHeureDep(LocalTime heureDep) {
        HeureDep = heureDep;
    }

    public LocalTime getHeureArr() {
        return HeureArr;
    }

    public void setHeureArr(LocalTime heureArr) {
        HeureArr = heureArr;
    }

    public Float getPrix() {
        return prix;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public int getNombrePlaceDispo() {
        return NombrePlaceDispo;
    }

    public void setNombrePlaceDispo(int nombrePlaceDispo) {
        NombrePlaceDispo = nombrePlaceDispo;
    }

    public MoyenTransport getMoyenTransport() {
        return moyenTransport;
    }

    public void setMoyenTransport(MoyenTransport moyenTransport) {
        this.moyenTransport = moyenTransport;
    }

    public Hebergement getHebergement() {
        return hebergement;
    }

    public void setHebergement(Hebergement hebergement) {
        this.hebergement = hebergement;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Voyage{" +
                "id=" + id +
                ", depart='" + depart + '\'' +
                ", destination='" + destination + '\'' +
                ", DateDep=" + DateDep +
                ", DateArr=" + DateArr +
                ", prix=" + prix +
                ", NombrePlaceDispo=" + NombrePlaceDispo +
                '}';
    }
}