package com.example.instagramclone.Models;

public class LocationModel {
    private String name;
    private String address;
    private double lat;
    private double len;

    public LocationModel(String name, String address, double lat, double len) {
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.len = len;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddess() {
        return address;
    }

    public void setAddess(String addess) {
        this.address = addess;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLen() {
        return len;
    }

    public void setLen(double len) {
        this.len = len;
    }
}
