package com.example.locationapp.data.sources.local;

import androidx.lifecycle.LiveData;

import com.example.locationapp.data.sources.model.preferlocation.Location;

import java.util.List;

public interface LocalRepository {
    void insertLocation(Location location);

    LiveData<List<Location>> getAllLocationFromDb();
}
