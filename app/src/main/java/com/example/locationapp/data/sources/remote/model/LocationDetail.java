package com.example.locationapp.data.sources.remote.model;

public class LocationDetail {
    private String code;
    private String name;
    private String description;
    private double lat;
    private double lng;

    public LocationDetail(String code, String name, String description, double lat, double lng) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
