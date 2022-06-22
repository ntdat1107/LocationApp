package com.example.locationapp.data.sources.remote.model.preferlocation;

public class Location {
    private String id;
    private String code;
    private String name;
    private String image;


    public Location(String id, String code, String name, String image) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}