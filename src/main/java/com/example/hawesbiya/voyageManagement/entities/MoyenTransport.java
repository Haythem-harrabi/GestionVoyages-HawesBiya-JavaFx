package com.example.hawesbiya.voyageManagement.entities;

import java.sql.PreparedStatement;

public class MoyenTransport {


    private int id;
    private String categorieMoyen;
    private String typeMoyen;
    private String idModele;


    public MoyenTransport(int id, String categorieMoyen, String typeMoyen, String idModele) {
        this.id = id;
        this.categorieMoyen = categorieMoyen;
        this.typeMoyen = typeMoyen;
        this.idModele = idModele;
    }

    public MoyenTransport() {
        this.id=1;
    }

    public MoyenTransport(String categorieMoyen, String typeMoyen, String idModele) {
        this.categorieMoyen = categorieMoyen;
        this.typeMoyen = typeMoyen;
        this.idModele = idModele;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategorieMoyen() {
        return categorieMoyen;
    }

    public void setCategorieMoyen(String categorieMoyen) {
        this.categorieMoyen = categorieMoyen;
    }

    public String getTypeMoyen() {
        return typeMoyen;
    }

    public void setTypeMoyen(String typeMoyen) {
        this.typeMoyen = typeMoyen;
    }

    public String getIdModele() {
        return idModele;
    }

    public void setIdModele(String idModele) {
        this.idModele = idModele;
    }

    public String toString() {
        return  "Type : " + this.typeMoyen + " ;  Model :" + this.idModele;
    }



}