package com.example.locationapp.data.sources.remote.model.preferlocation;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return locations.equals(data.locations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locations);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < this.locations.size(); i++) {
            if (i != this.locations.size() - 1) {
                temp.append(this.locations.get(i).toString()).append(",");
            } else {
                temp.append(this.locations.get(i).toString());
            }
        }
        return "\"data\": {\n" +
                "\"locations\": [\n" +
                temp +
                "\n]\n" +
                "}";
    }
}
