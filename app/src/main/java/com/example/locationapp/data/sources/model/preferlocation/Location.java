package com.example.locationapp.data.sources.model.preferlocation;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "prefer_locations")
public class Location implements Serializable {
    @PrimaryKey
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id.equals(location.id) && code.equals(location.code) && name.equals(location.name) && image.equals(location.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, image);
    }

    @NonNull
    @Override
    public String toString() {
        return "{\n" +
                "\"id\": \"" + this.id + "\",\n" +
                "\"code\": \"" + this.code + "\",\n" +
                "\"name\": \"" + this.name + "\",\n" +
                "\"image\": \"" + this.image + "\"\n" +
                "}";
    }
}