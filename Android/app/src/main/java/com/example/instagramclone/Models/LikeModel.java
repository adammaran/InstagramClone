package com.example.instagramclone.Models;

import com.google.gson.annotations.SerializedName;

public class LikeModel {
    @SerializedName("username")
    private String username;

    @SerializedName("timestamp")
    private String timestamp;

    public LikeModel(String username, String timestamp) {
        this.username = username;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
