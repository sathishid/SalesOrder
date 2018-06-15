package com.ara.sunflowerorder.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class Invoice {
    private int id;
    private String date;
    private Customer customer;
    private double invoiceAmount;
    private double paidAmount;
    private double balanceAmount;
    private double collectedAmount;
    private double pendingAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public double getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(double collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    public double getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(double pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public static List<Invoice> fromJSONArray(String json) {
        if (json == null || json.isEmpty()) {
            return new ArrayList<>(0);
        }
        Gson gson = getGson();
        Invoice[] invoices = gson.fromJson(json, Invoice[].class);
        return Arrays.asList(invoices);
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
