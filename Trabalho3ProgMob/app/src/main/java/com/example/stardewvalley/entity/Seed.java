package com.example.stardewvalley.entity;

public class Seed {
    private String descricao;
    private double latitude;
    private double longitude;
    private String email;
    private String endereco;  // Novo campo

    // Construtor vazio necessário para a desserialização com Firebase
    public Seed() {}

    public Seed(String descricao, double latitude, double longitude, String email, String endereco) {
        this.descricao = descricao;
        this.latitude = latitude;
        this.longitude = longitude;
        this.email = email;
        this.endereco = endereco;
    }

    // Getters e Setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}

