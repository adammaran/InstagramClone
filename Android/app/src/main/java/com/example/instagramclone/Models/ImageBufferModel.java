package com.example.instagramclone.Models;

import com.google.gson.annotations.SerializedName;

public class ImageBufferModel {

    @SerializedName("type")
    private String type;

    @SerializedName("data")
    private byte[] data;

    public ImageBufferModel() {
    }

    public ImageBufferModel(String type, byte[] data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] bytes) {
        this.data = bytes;
    }
}
