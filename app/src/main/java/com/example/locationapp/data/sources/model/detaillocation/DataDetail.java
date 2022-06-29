package com.example.locationapp.data.sources.model.detaillocation;

import androidx.annotation.NonNull;

import java.util.Objects;

public class DataDetail {
    private LocationDetail location;

    public DataDetail(LocationDetail location) {
        this.location = location;
    }

    public LocationDetail getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataDetail that = (DataDetail) o;
        return Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @NonNull
    @Override
    public String toString() {
        return "\"data\": {\n" +
                this.location.toString() +
                "\n}";
    }
}
