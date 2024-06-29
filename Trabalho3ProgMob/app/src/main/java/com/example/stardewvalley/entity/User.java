package com.example.stardewvalley.entity;

public class User {
    private String userID;
    private String nome;
    private String email;
    private String senha;
    private String profileImageUrl; // Novo campo para armazenar a URL da imagem de perfil


    public User() {
        // Construtor vazio necessário para Firestore
    }

    public User(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public User(String userID, String nome, String email, String senha) {
        this.userID = userID;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.profileImageUrl = null; // Definir profileImageUrl como null por padrão
    }

    public User(String userID, String nome, String email, String senha, String profileImageUrl) {
        this.userID = userID;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.profileImageUrl = profileImageUrl;
    }

    // Novo construtor com seedId e cityId
    public User(String userID, String nome, String email, String senha, String profileImageUrl, String seedId, String cityId) {
        this.userID = userID;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.profileImageUrl = profileImageUrl;

    }

    // Getters e Setters
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

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }


}
