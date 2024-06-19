package com.example.stardewvalley.model;

import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private String imageUrl;
    private double price;

    public Item() {
        // Construtor vazio necessário para o Firestore
    }

    public Item(String name, String imageUrl, double price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
