package com.example.hawesbiya.voyageManagement.services;

import com.example.hawesbiya.voyageManagement.entities.Hebergement;
import com.example.hawesbiya.voyageManagement.entities.MoyenTransport ;
import com.example.hawesbiya.voyageManagement.entities.Voyage;
import com.example.hawesbiya.utils.MyDB;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
public class ServiceVoyage implements services.IService<Voyage> {

    public Connection con;
    public Statement ste;

    public ServiceVoyage(){
        con= MyDB.getInstance().getCon();
    }

    @Override
    public void add(Voyage voyage) throws SQLException {
        String req="insert into voyage (depart,destination,date_dep,date_arr,heure_dep,heure_arr,prix,nombre_place_dispo,moyen_transport_id,hebergement_id,description) values (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(req);
        ps.setString(1, voyage.getDepart());
        ps.setString(2, voyage.getDestination());
        ps.setDate(3, Date.valueOf(voyage.getDateDep()));
        ps.setDate(4, Date.valueOf(voyage.getDateArr()));
        ps.setTime(5, Time.valueOf("00:00:00"));
        ps.setTime(6,Time.valueOf("00:00:00"));
        ps.setFloat(7, voyage.getPrix());
        ps.setInt(8, voyage.getNombrePlaceDispo());
        ps.setInt(9, voyage.getMoyenTransport().getId());
        ps.setInt(10, voyage.getHebergement().getId());
        ps.setString(11, voyage.getDescription());
        ps.executeUpdate();
        System.out.println("Voyage added successfully!");
        //Voyage.CreatedVoy++;
    }

    @Override
    public void update(Voyage voyage) throws SQLException {
        String req = "update voyage set depart=?,destination=?,date_dep=?,date_arr=?,prix=?,nombre_place_dispo=?,moyen_transport_id=?,hebergement_id=?,description=? where id=? ";
        PreparedStatement ps = con.prepareStatement(req);
        ps.setString(1, voyage.getDepart());
        ps.setString(2, voyage.getDestination());
        ps.setDate(3, Date.valueOf(voyage.getDateDep()));
        ps.setDate(4, Date.valueOf(voyage.getDateArr()));
//        ps.setTime(5, Time.valueOf(voyage.getHeureDep()));
//        ps.setTime(6, Time.valueOf(voyage.getHeureArr()));
        ps.setFloat(5, voyage.getPrix());
        ps.setInt(6, voyage.getNombrePlaceDispo());
        ps.setInt(7, voyage.getMoyenTransport().getId());
        ps.setInt(8, voyage.getHebergement().getId());
        ps.setString(9, voyage.getDescription());
        ps.setInt(10, voyage.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(Voyage voyage) throws SQLException {
        String req = "delete from voyage where id=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,voyage.getId());
        pre.executeUpdate();
    }


    @Override
    public List<Voyage> afficher() throws SQLException {
        List<Voyage> voy = new ArrayList<>();
        String req = "select * from voyage";
        ste = con.createStatement();
        ResultSet res =ste.executeQuery(req);
        while (res.next()){
            int id=res.getInt(1);
            String depart =res.getString(2);
            String destination =res.getString(3);
            LocalDate dateDep= res.getDate(4).toLocalDate();
            LocalDate dateArr= res.getDate(5).toLocalDate();
            Float prix=res.getFloat(8);
            int nbrPlaces=res.getInt(9);
            int moyenTransportid=res.getInt(10);
            int heberegement=res.getInt(11);
            ServiceMoyen sm = new ServiceMoyen();
            MoyenTransport moyen= sm.getMoyenById(moyenTransportid);

            String desc = res.getString(12);

            //JUST FOR TESTING
            Hebergement h=new Hebergement(heberegement);

            Voyage v = new Voyage(id,depart,destination,dateDep,dateArr,prix,nbrPlaces,moyen,h,desc);
            voy.add(v);
        }
        return voy;
    }


    public boolean ExistVoyage(Voyage v) throws SQLException {
        String req = "SELECT * FROM voyage WHERE depart=? AND destination=? AND date_dep=? AND date_arr=? AND prix=? AND nombre_place_dispo=? AND moyen_transport_id=? AND hebergement_id=? AND description=?";
        PreparedStatement ps = con.prepareStatement(req);
        ps.setString(1,v.getDepart());
        ps.setString(2,v.getDestination());
        ps.setString(3,String.valueOf(v.getDateDep()));
        ps.setString(4,String.valueOf(v.getDateArr()));
        ps.setString(5, String.valueOf(v.getPrix()));
        ps.setInt(6,v.getNombrePlaceDispo());
        ps.setInt(7,v.getMoyenTransport().getId());
        ps.setInt(8,v.getHebergement().getId());
        ps.setString(9, v.getDescription());
        ResultSet res = ps.executeQuery();
        return res.next();
    }

    }


