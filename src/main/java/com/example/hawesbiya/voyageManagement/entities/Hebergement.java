package com.example.hawesbiya.voyageManagement.entities;

public class Hebergement {
    private int id;

    public Hebergement() {
        this.id=125;
    }

    public Hebergement(int id) {
        this.id = 125;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Hebergement{" +
                "id=" + id +
                '}';
    }
}
