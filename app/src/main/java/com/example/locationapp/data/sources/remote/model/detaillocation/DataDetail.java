package com.example.locationapp.data.sources.remote.model.detaillocation;

public class DataDetail {
    private LocationDetail location;

    public DataDetail(LocationDetail location) {
        this.location = location;
    }

    public LocationDetail getLocation() {
        return location;
    }
}
