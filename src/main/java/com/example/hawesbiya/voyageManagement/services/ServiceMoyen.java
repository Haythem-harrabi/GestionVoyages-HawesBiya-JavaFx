package com.example.hawesbiya.voyageManagement.services;

import com.example.hawesbiya.voyageManagement.entities.Hebergement;
import com.example.hawesbiya.voyageManagement.entities.MoyenTransport ;
import com.example.hawesbiya.voyageManagement.entities.Voyage;
import com.example.hawesbiya.utils.MyDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceMoyen implements services.IService<MoyenTransport> {
    public Connection con;
    public Statement ste;

    public ServiceMoyen(){
        con= MyDB.getInstance().getCon();
    }
    @Override
    public void add(MoyenTransport moyenTransport) throws SQLException {
        String req="insert into moyen_transport (categorie_moyen,type_moyen,id_modele) values (?,?,?)";
        PreparedStatement ps = con.prepareStatement(req);
        ps.setString(1, moyenTransport.getCategorieMoyen());
        ps.setString(2, moyenTransport.getTypeMoyen());
        ps.setString(3, moyenTransport.getIdModele());
        ps.executeUpdate();
        System.out.println("Moyen added successfully!");

    }

    @Override
    public void update(MoyenTransport moyenTransport) throws SQLException {
        String req = "update moyen_transport set categorie_moyen=?,type_moyen=?,id_modele=? where id=? ";
        PreparedStatement ps = con.prepareStatement(req);
        ps.setString(1, moyenTransport.getCategorieMoyen());
        ps.setString(2, moyenTransport.getTypeMoyen());
        ps.setString(3, moyenTransport.getIdModele());
        ps.setInt(4, moyenTransport.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(MoyenTransport moyenTransport) throws SQLException {
        String req = "delete from moyen_transport where id=? ";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1,moyenTransport.getId());
        pre.executeUpdate();
    }

    @Override
    public List<MoyenTransport> afficher() throws SQLException {
        List<MoyenTransport> moy = new ArrayList<>();
        String req = "select * from moyen_transport";
        ste = con.createStatement();
        ResultSet res =ste.executeQuery(req);
        while (res.next()) {
            int id = res.getInt(1);
            String categorie = res.getString(2);
            String type = res.getString(3);
            String modele = res.getString(4);
            MoyenTransport m = new MoyenTransport(id,categorie,type,modele);
            moy.add(m);
        }
        return moy;

    }


    public MoyenTransport getMoyenById(int id) throws SQLException {
        MoyenTransport moyen = null;
        String req = "SELECT * FROM moyen_transport WHERE id=?";
        PreparedStatement ps = con.prepareStatement(req);
        ps.setInt(1, id);
        ResultSet res = ps.executeQuery();
        while (res.next()) {
            String categorie=res.getString(2);
            String type=res.getString(3);
            String modele=res.getString(4);
            moyen=new MoyenTransport(id,categorie,type,modele);

        }
        return moyen;
    }

    public boolean ExistMoyen(MoyenTransport moyen) throws SQLException {
        String req = "SELECT * FROM moyen_transport WHERE  categorie_moyen=? AND type_moyen=? AND id_modele=?";
        PreparedStatement ps = con.prepareStatement(req);
        ps.setString(1,moyen.getCategorieMoyen());
        ps.setString(2,moyen.getTypeMoyen());
        ps.setString(3,moyen.getIdModele());
        ResultSet res = ps.executeQuery();
        return res.next();
    }


    public List<MoyenTransport> filterMoyens(String typesearch, String model) throws SQLException {
        List<MoyenTransport> moy = new ArrayList<>();
        String query="select * from moyen_transport WHERE 1";

        if (typesearch != null && ! typesearch.isEmpty()  ){
            query+=" AND `type_moyen` ='"+typesearch+"'";
        }
        if (model!=null && ! model.isEmpty() ){
            query+=" AND `id_modele` LIKE '"+model+"%'";
        }

        System.out.println(query);
        ste = con.createStatement();
        ResultSet res =ste.executeQuery(query);
        while (res.next()){
            int id = res.getInt(1);
            String categorie = res.getString(2);
            String type = res.getString(3);
            String modele = res.getString(4);
            MoyenTransport m = new MoyenTransport(id,categorie,type,modele);
            moy.add(m);
        }
        return moy;
    }
}