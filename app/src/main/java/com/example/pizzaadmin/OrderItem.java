package com.example.pizzaadmin;
public class OrderItem {
    private String itemId;
    private String name;
    private double price;

    public OrderItem(String itemId, String name, double price) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
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
}
