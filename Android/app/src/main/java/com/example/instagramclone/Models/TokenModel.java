package com.example.instagramclone.Models;

import com.google.gson.annotations.SerializedName;

public class TokenModel {
    @SerializedName("token")
    private String token;
    @SerializedName("user")
    private UserModel userModel;

    public TokenModel(String token, UserModel userModel) {
        this.token = token;
        this.userModel = userModel;
    }

    public UserModel getCurrentUserModel() {
        return userModel;
    }

    public void setCurrentUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
