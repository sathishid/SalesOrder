package com.ara.sunflowerorder.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.ara.sunflowerorder.utils.AppConstants.getGson;

public class User {
    @SerializedName("userid")
    private int id;
    private String userId;
    @SerializedName("username")
    private String userName;
    @SerializedName("branch_id")
    private String branchId;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String toJson() {
        Gson gson = getGson();
        return gson.toJson(this);
    }

    public static User fromJson(String message) {

        try {
            JSONObject reader = new JSONObject(message);

            User user = new User();


            user.id = Integer.parseInt(reader.getString("user_id"));
            user.branchId = reader.getString("user_branch_ids");
            user.userName = reader.getString("user_name");

            return user;
        } catch (JSONException jsonException) {
            return null;
        }
    }
}
