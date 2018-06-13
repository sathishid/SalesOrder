package com.ara.sunflowerorder.models;

import com.google.gson.Gson;

public class OrderItem {
    private int id;
    private Product product;
    private int quantity;
    private double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static OrderItem fromJson(String json) {
        if (json == null || json.isEmpty()) {
            return new OrderItem();
        }
        Gson gson = new Gson();
        OrderItem orderItem = gson.fromJson(json, OrderItem.class);
        return orderItem;
    }
}
