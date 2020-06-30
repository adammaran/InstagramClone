package com.example.instagramclone.Models;

import com.google.gson.annotations.SerializedName;

public class CommentModel {
    @SerializedName("username")
    private String username;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("comment")
    private String commentText;

    public CommentModel(String username, String timestamp, String commentText) {
        this.username = username;
        this.timestamp = timestamp;
        this.commentText = commentText;
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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
