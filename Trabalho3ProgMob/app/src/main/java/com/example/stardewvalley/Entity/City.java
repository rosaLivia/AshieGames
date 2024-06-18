package com.example.stardewvalley.Entity;

public class City {
    private String cidadeID; // Alterado para String para compatibilidade com Firebase
    private String cidade;
    private String estado;

    // Construtor vazio necessário para a desserialização com Firebase
    public City() {}

    public City(String cidadeID, String cidade, String estado) {
        this.cidadeID = cidadeID;
        this.cidade = cidade;
        this.estado = estado;
    }

    // Getters e setters
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

    @Override
    public String toString() {
        return cidade + " - " + estado;
    }
}
