package com.example.instagramclone.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedItemModel {

    @SerializedName("_id")
    private String feedUUID;

    private transient UserModel user;
    private transient int likeCount;

    @SerializedName("image")
    private ImageBufferModel imageBuffer;

    public ImageBufferModel getImageBuffer() {
        return imageBuffer;
    }

    public void setImageBuffer(ImageBufferModel imageBuffer) {
        this.imageBuffer = imageBuffer;
    }

    private String imageURL;

    private transient int commentCount;

    @SerializedName("likes")
    private List<LikeModel> likeList;

    @SerializedName("comments")
    private List<CommentModel> commentList;

    @SerializedName("user_id")
    private String userUid;

    @SerializedName("description")
    private String description;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("location")
    private String locationString;

    @SerializedName("username")
    private String fullname;

    private transient LocationModel location;

    public FeedItemModel() {

    }

    public FeedItemModel(String imageURL, List<LikeModel> likeList, List<CommentModel> commentList, String userUid, String description, String timestamp, String locationString) {
        this.imageURL = imageURL;
        this.likeList = likeList;
        this.commentList = commentList;
        this.userUid = userUid;
        this.description = description;
        this.timestamp = timestamp;
        this.locationString = locationString;
    }

    public FeedItemModel(String description, String location) {
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLocationString() {
        return locationString;
    }

    public void setLocationString(String locationString) {
        this.locationString = locationString;
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
