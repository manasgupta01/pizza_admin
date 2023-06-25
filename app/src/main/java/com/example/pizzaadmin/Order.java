package com.example.pizzaadmin;

import java.util.List;

public class Order {
    private String userId;
    private String itemId;
    private String name;
    private List<OrderItem> orderItems;
    private double price;
    private byte[] imageBytes;
    private double totalAmount;
    private String status;
    private String orderId;

    // Default constructor (required for Firestore deserialization)
    public Order() {
    }

    public Order(String userId, String itemId, String name, double price, byte[] imageBytes, double totalAmount, String status, String orderId) {
        this.userId = userId;
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.imageBytes = imageBytes;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getPrice();
        }
        return totalPrice;
    }
}
