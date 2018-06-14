package com.ara.sunflowerorder.models;

import com.google.gson.Gson;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class DeliveryItem {
    private int id;
    private Product product;
    private int quantity;
    private int accept;
    private int reject;

    public DeliveryItem() {
    }

    public DeliveryItem(int id, Product product, int quantity, int accept, int reject) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.accept = accept;
        this.reject = reject;
    }

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

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
    }

    public int getReject() {
        return reject;
    }

    public void setReject(int reject) {
        this.reject = reject;
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public static DeliveryItem fromJSON(String result) {
        if (result == null || result.isEmpty()) {
            return new DeliveryItem();
        }
        Gson gson = new Gson();
        DeliveryItem deliveryItem = gson.fromJson(result, DeliveryItem.class);
        return deliveryItem;
    }
}
