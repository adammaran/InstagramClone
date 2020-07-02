package com.example.instagramclone.Models;

import com.google.gson.annotations.SerializedName;

public class CurrentUserModel {

    @SerializedName("user")
    private static UserModel instance = null;

    private CurrentUserModel() {

    }

    public static UserModel getInstance() {
        return instance;
    }

    public static void setInstance(UserModel currentUserModel) {
        instance = currentUserModel;
    }
}
