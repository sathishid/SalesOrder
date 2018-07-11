package com.ara.sunflowerorder.models.view;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class CollectionReport {
    private int collectionId;
    private String collectionNo;
    private String date;
    private String customerName;
    private String modeOfPayment;
    private double invoiceAmt;
    private double collectionAmt;
    private double balanceAmt;


    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionNo() {
        return collectionNo;
    }

    public void setCollectionNo(String collectionNo) {
        this.collectionNo = collectionNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public double getInvoiceAmt() {
        return invoiceAmt;
    }

    public void setInvoiceAmt(double invoiceAmt) {
        this.invoiceAmt = invoiceAmt;
    }

    public double getCollectionAmt() {
        return collectionAmt;
    }

    public void setCollectionAmt(double collectionAmt) {
        this.collectionAmt = collectionAmt;
    }

    public double getBalanceAmt() {
        return balanceAmt;
    }

    public void setBalanceAmt(double balanceAmt) {
        this.balanceAmt = balanceAmt;
    }

    public static List<CollectionReport> fromJsonArray(String messsage) {
        Gson gson = getGson();
        CollectionReport[] collectionReports = gson.fromJson(messsage, CollectionReport[].class);
        if (collectionReports == null) {
            return new ArrayList<>();
        }

        return Arrays.asList(collectionReports);

    }
}
