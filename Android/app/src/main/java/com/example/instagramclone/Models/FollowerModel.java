package com.example.instagramclone.Models;

import com.google.gson.annotations.SerializedName;

public class FollowerModel {

    @SerializedName("_id")
    private String ID;

    @SerializedName("username")
    private String username;

    @SerializedName("fullName")
    private String fullname;

    @SerializedName("avatar")
    private ImageBufferModel avatar;

    public FollowerModel() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public ImageBufferModel getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageBufferModel avatar) {
        this.avatar = avatar;
    }
}
