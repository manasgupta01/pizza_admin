package com.example.pizzaadmin;

public class Order {
    private String orderId;
    private String itemId;
    private String name;
    private double price;
    private byte[] imageBytes;
    private String status;

    public Order() {
        // Default constructor required for Firebase Firestore deserialization
    }

    public Order(String orderId, String itemId, String name, double price, byte[] imageBytes, String status) {
        this.orderId = orderId;
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.imageBytes = imageBytes;
        this.status = status;
    }

    public Order(String orderId, String itemId, String name, double price, String status) {
    }

    // Getters and setters (omitted for brevity)


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

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

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

