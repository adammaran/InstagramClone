package com.example.instagramclone.Models;

public class CurrentUserModel{
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
