package com.ara.sunflowerorder.models;

import java.util.ArrayList;
import java.util.List;

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
}
