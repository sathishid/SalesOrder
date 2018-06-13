package com.ara.sunflowerorder.models;

import com.ara.sunflowerorder.utils.AppConstants;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.UOM_ARRAY;
import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class Product {
    private int id;
    private String name;
    private int availableQty;
    private double price;
    private Brand brand;
    private UOM uom;
    private String code;

    public Product() {
    }

    public Product(int id, String name, int availableQty, double price, Brand brand, UOM uom, String code) {
        this.id = id;
        this.name = name;
        this.availableQty = availableQty;
        this.price = price;
        this.brand = brand;
        this.uom = uom;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public UOM getUom() {
        return uom;
    }

    public void setUom(UOM uom) {
        this.uom = uom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static List<Product> fromJSONArray(String json) {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1, "GMail", 1, 45.5,
                new Brand(1, "Google"),
                UOM_ARRAY[4],
                "GM"
        ));
        products.add(new Product(1, "IPhone SE", 5, 45.5,
                new Brand(1, "Apple"),
                UOM_ARRAY[3], "IPhone"));
        return products;

    }

    public String toString() {
        return this.name;
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public static Product fromJSON(String result) {
        if (result == null || result.isEmpty()) {
            return new Product();
        }
        Gson gson = new Gson();
        Product product = gson.fromJson(result, Product.class);
        return product;
    }
}
