package com.ara.sunflowerorder.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class Brand {
    private int id;
    private String name;

    public Brand(){ }
    public Brand(int id, String name) {
        this.id = id;
        this.name = name;
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

    public static List<Brand> fromJSONArray(String json) {
        ArrayList<Brand> brands=new ArrayList<>(3);

        brands.add(new Brand(1,"Google"));
        brands.add(new Brand(1,"Apple"));
        brands.add(new Brand(1,"Microsoft"));
        return brands;
    }

    @Override
    public String toString() {
        return name;
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public static Brand fromJSON(String result) {
        if (result == null || result.isEmpty()) {
            return new Brand(-1, "");
        }
        Gson gson = new Gson();
        Brand brand = gson.fromJson(result, Brand.class);
        return brand;
    }
}
