package com.example.instagramclone.Models;

import com.google.gson.annotations.SerializedName;

public class UserStatsModel {
    @SerializedName("posts")
    private int postCount;

    @SerializedName("followers")
    private int followerCount;

    @SerializedName("following")
    private int followingCount;

    public UserStatsModel(int postCount, int followerCount, int followingCount) {
        this.postCount = postCount;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }
}
