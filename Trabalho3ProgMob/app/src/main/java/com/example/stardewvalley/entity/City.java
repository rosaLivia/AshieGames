package com.example.stardewvalley.entity;

public class City {
    private String cidadeID;
    private String cidade;
    private String estado;
    private String email;

    public City() {}

    public City(String cidadeID, String cidade, String estado) {
        this.cidadeID = cidadeID;
        this.cidade = cidade;
        this.estado = estado;
    }

    public String getCidadeID() {
        return cidadeID;
    }

    public void setCidadeID(String cidadeID) {
        this.cidadeID = cidadeID;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return cidade + " - " + estado;
    }
}
