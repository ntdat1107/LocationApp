package com.example.locationapp.data.sources.remote;

import java.util.List;

public class Data {
    private List<Location> locations;

    public Data(List<Location> locations) {
        this.locations = locations;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
}
