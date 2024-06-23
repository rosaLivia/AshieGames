package com.example.stardewvalley.model;

import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private int imageResourceId;
    private double price;

    public Item() {
        // Construtor vazio necessário para o Firestore
    }

    public Item(String name, int imageResourceId, double price) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
