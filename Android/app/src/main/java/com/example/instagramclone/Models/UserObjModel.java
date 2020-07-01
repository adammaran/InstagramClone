package com.example.instagramclone.Models;

import com.google.gson.annotations.SerializedName;

public class UserObjModel {

    @SerializedName("stats")
    private UserStatsModel userStats;

    @SerializedName("user")
    private UserModel user;

    public UserObjModel() {
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserStatsModel getUserStats() {
        return userStats;
    }

    public void setUserStats(UserStatsModel userStats) {
        this.userStats = userStats;
    }
}
