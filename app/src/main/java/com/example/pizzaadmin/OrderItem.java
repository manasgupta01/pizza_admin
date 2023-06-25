package com.example.pizzaadmin;

public class OrderItem {
    private String itemId;
    private String name;
    private double price;
    private byte[] imageBytes;
    private String status;

    public OrderItem() {
        // Default constructor required for Firestore serialization
    }

    public OrderItem(String itemId, String name, double price, byte[] imageBytes, String status) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.imageBytes = imageBytes;
        this.status = status;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public String getStatus() {
        return status;
    }
}


