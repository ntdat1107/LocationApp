package com.example.locationapp.data.sources.model.detaillocation;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "location")
public class LocationDetail implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String code;
    private String name;
    private String description;
    private double lat;
    private double lng;
    private double timestamp;

    public LocationDetail(@NonNull String id, String code, String name, String description, double lat, double lng) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.lat = lat;
        this.lng = lng;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationDetail that = (LocationDetail) o;
        return Double.compare(that.lat, lat) == 0 && Double.compare(that.lng, lng) == 0 && Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, description, lat, lng);
    }

    @NonNull
    @Override
    public String toString() {
        return "\"location\": {\n" +
                "\"code\": \"" + this.code + "\",\n" +
                "\"name\": \"" + this.name + "\",\n" +
                "\"description\": \"" + this.description + "\",\n" +
                "\"lat\": " + this.lat + ",\n" +
                "\"lng\": " + this.lng + "\n" +
                "}";
    }

    public double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(double timestamp) {
        this.timestamp = timestamp;
    }
}
