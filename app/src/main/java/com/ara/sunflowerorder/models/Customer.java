package com.ara.sunflowerorder.models;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class Customer {
    private int id;
    private String name;

    public Customer(int id, String name) {
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

    @Override
    public String toString() {
        return getName();
    }

    public static Customer fromJSON(String json) {
        if (json == null || json.isEmpty()) {
            return new Customer(-1, "");
        }
        Gson gson = new Gson();
        Customer customer = gson.fromJson(json, Customer.class);
        return customer;
    }

    public static List<Customer> fromJSONArray(String json) {
        if (json == null || json.isEmpty()) {
            return new ArrayList<>(0);
        }

       /* Gson gson = new Gson();
        Customer[] customers = gson.fromJson(json, Customer[].class);
        return Arrays.asList(customers);*/
        try {

            JSONArray jsonArray = new JSONArray(json);
            Customer customer = null;
            ArrayList<Customer> customers = new ArrayList<>(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                customer = new Customer(i, jsonArray.getString(i));
                customers.add(customer);
            }
            return customers;
        } catch (JSONException jsonException) {
            return new ArrayList<>(0);
        }
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }
}
