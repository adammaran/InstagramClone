package com.example.instagramclone.Models;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class FeedItemModel {

    private String feedUUID;
    private UserModel user;
    private int likeCount;
    private String imageURL;
    private int commentCount;

    @SerializedName("image")
    private File imageFile;

    @SerializedName("description")
    private String description;
    private String timestamp;

    private String locationString;

//    @SerializedName("location")
    private LocationModel location;

    public FeedItemModel() {

    }

    public FeedItemModel(File imageFile, String description, String location) {
        this.imageFile = imageFile;
        this.description = description;
        this.locationString = location;
    }

    public FeedItemModel(String imageURL, String description, LocationModel location) {
        this.imageURL = imageURL;
        this.description = description;
        this.location = location;
    }

    public FeedItemModel(String feedUUID, UserModel user, int likeCount, String imageURL, int commentCount, String description, String timestamp, LocationModel location) {
        this.feedUUID = feedUUID;
        this.user = user;
        this.likeCount = likeCount;
        this.imageURL = imageURL;
        this.commentCount = commentCount;
        this.description = description;
        this.timestamp = timestamp;
        this.location = location;
    }

    public String getLocationString() {
        return locationString;
    }

    public void setLocationString(String locationString) {
        this.locationString = locationString;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
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

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }
}
