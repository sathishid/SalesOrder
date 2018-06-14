package com.ara.sunflowerorder.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class Delivery {
    private int id;
    private Customer customer;
    private String deliveryDate;
    private Approval approval;
    private List<DeliveryItem> deliveryItems;
    private String remarks;

    public Delivery() {
    }

    public Delivery(int id, Customer customer, String deliveryDate,
                    Approval approval, String remarks) {
        this.id = id;
        this.customer = customer;
        this.deliveryDate = deliveryDate;
        this.approval = approval;
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Approval getApproval() {
        return approval;
    }

    public void setApproval(Approval approval) {
        this.approval = approval;
    }

    public List<DeliveryItem> getDeliveryItems() {
        if (deliveryItems == null)
            deliveryItems = new ArrayList<>();
        return deliveryItems;
    }

    public void setDeliveryItems(List<DeliveryItem> deliveryItems) {
        this.deliveryItems = deliveryItems;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public static List<Delivery> fromJSONArray(String json) {
        ArrayList<Delivery> deliveries = new ArrayList<>(3);


        Approval approval = Approval.fromJSONArray(null).get(0);
        Delivery delivery = new Delivery(0, null, "02-03-2018", approval, "Remarks");
        Product product = Product.fromJSONArray(null).get(0);


        DeliveryItem deliveryItem = new DeliveryItem(1, product, 5, 4, 1);
        product = Product.fromJSONArray(null).get(1);
        delivery.getDeliveryItems().add(deliveryItem);
        deliveryItem = new DeliveryItem(1, product, 6, 3, 3);

        delivery.getDeliveryItems().add(deliveryItem);

        deliveries.add(delivery);

        return deliveries;
    }

    @Override
    public String toString() {
        return id + "";
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public static Delivery fromJSON(String result) {
        if (result == null || result.isEmpty()) {
            return new Delivery();
        }
        Gson gson = new Gson();
        Delivery delivery = gson.fromJson(result, Delivery.class);
        return delivery;
    }
}
