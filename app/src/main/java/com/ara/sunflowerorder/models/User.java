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
        Gson gson = getGson();
        try {
            JSONArray jsonArray = new JSONArray(message);
            User user = new User();
            JSONObject reader=jsonArray.getJSONObject(0);
            user.id = Integer.parseInt(reader.getString("userid"));
            user.userName = reader.getString("username");
            return user;
        } catch (JSONException jsonException) {
            return null;
        }
    }
}
