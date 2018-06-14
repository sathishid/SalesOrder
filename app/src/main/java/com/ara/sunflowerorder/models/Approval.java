package com.ara.sunflowerorder.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class Approval {
    private int id;
    private String date;

    public Approval() {
    }

    public Approval(int id, String date) {
        this.id = id;
        this.date = date;
    }

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


    public static List<Approval> fromJSONArray(String json) {
        ArrayList<Approval> approvals = new ArrayList<>(3);

        approvals.add(new Approval(1, "02-03-2018"));
        approvals.add(new Approval(2, "03-02-2018"));
        approvals.add(new Approval(3, "04-03-2018"));
        return approvals;
    }

    @Override
    public String toString() {
        return id + "";
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public static Approval fromJSON(String result) {
        if (result == null || result.isEmpty()) {
            return new Approval(-1, "02-03-2018");
        }
        Gson gson = new Gson();
        Approval approval = gson.fromJson(result, Approval.class);
        return approval;
    }
}
