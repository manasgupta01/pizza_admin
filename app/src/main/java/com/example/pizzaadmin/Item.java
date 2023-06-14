package com.example.pizzaadmin;

public class Item {
    private String name;
    private double price;
    private byte[] image;

    public Item() {
        // Default constructor required for Firestore
    }

    public Item(String name, double price, byte[] image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

