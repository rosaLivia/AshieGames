package com.example.stardewvalley.Entity;

public class Seed {
    private String enderecoID; // Alterado para String para compatibilidade com Firebase
    private String descricao;
    private double latitude;
    private double longitude;
    private String cityIDFK; // Alterado para String

    // Construtor vazio necessário para a desserialização com Firebase
    public Seed() {}

    public Seed(String enderecoID, String descricao, double latitude, double longitude, String cityIDFK) {
        this.enderecoID = enderecoID;
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityIDFK = cityIDFK;
    }

    // Getters e setters
    public String getEnderecoID() {
        return enderecoID;
    }

    public void setEnderecoID(String enderecoID) {
        this.enderecoID = enderecoID;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCityIDFK() {
        return cityIDFK;
    }

    public void setCityIDFK(String cityIDFK) {
        this.cityIDFK = cityIDFK;
    }
}
