package com.example.stardewvalley.Entity;

public class User {
    private String userID; // Alterado para String para compatibilidade com Firebase
    private String nome;
    private String email;
    private String senha;

    // Construtor vazio necessário para a desserialização com Firebase
    public User() {}

    public User(String userID, String nome, String email, String senha) {
        this.userID = userID;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Getters e setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
