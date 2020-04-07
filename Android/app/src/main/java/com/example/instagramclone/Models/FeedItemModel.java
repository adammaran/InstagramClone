package com.example.instagramclone.Models;

public class FeedItemModel {

    private String feedUUID;
    private UserModel user;
    private int likeCount;
    private String imageURL;
    private int commentCount;
    private String description;
    private String timestamp;
    private String location;

    public FeedItemModel(String feedUUID, UserModel user, int likeCount, String imageURL, int commentCount, String description, String timestamp, String location) {
        this.feedUUID = feedUUID;
        this.user = user;
        this.likeCount = likeCount;
        this.imageURL = imageURL;
        this.commentCount = commentCount;
        this.description = description;
        this.timestamp = timestamp;
        this.location = location;
    }

    public String getFeedUUID() {
        return feedUUID;
    }

    public void setFeedUUID(String feedUUID) {
        this.feedUUID = feedUUID;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
