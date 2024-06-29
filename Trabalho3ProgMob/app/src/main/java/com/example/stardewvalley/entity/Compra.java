package com.example.stardewvalley.entity;

public class Compra {
    private String itemId;
    private String nomeItem;
    private double precoItem;
    private String dataCompra;
    private String emailUsuario; // Novo campo adicionado

    public Compra() {
        // Construtor padrão necessário para chamadas de DataSnapshot.getValue(Compra.class)
    }

    public Compra(String itemId, String nomeItem, double precoItem, String dataCompra, String emailUsuario) {
        this.itemId = itemId;
        this.nomeItem = nomeItem;
        this.precoItem = precoItem;
        this.dataCompra = dataCompra;
        this.emailUsuario = emailUsuario; // Inicializa o novo campo
    }

    // Getters e Setters
    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }
    public String getNomeItem() { return nomeItem; }
    public void setNomeItem(String nomeItem) { this.nomeItem = nomeItem; }
    public double getPrecoItem() { return precoItem; }
    public void setPrecoItem(double precoItem) { this.precoItem = precoItem; }
    public String getDataCompra() { return dataCompra; }
    public void setDataCompra(String dataCompra) { this.dataCompra = dataCompra; }
    public String getEmailUsuario() { return emailUsuario; }
    public void setEmailUsuario(String emailUsuario) { this.emailUsuario = emailUsuario; }
}
