package com.example.locationapp.data.sources.local;

import androidx.lifecycle.LiveData;

import com.example.locationapp.data.sources.model.preferlocation.Location;

import java.util.List;

import javax.inject.Inject;

public class LocalRepositoryImpl implements LocalRepository {
    private final LocationDatabase locationDatabase;

    @Inject
    public LocalRepositoryImpl(LocationDatabase locationDatabase) {
        this.locationDatabase = locationDatabase;
    }


    @Override
    public void insertLocation(Location location) {
        locationDatabase.locationDao().insertLocation(location);
    }

    @Override
    public LiveData<List<Location>> getAllLocationFromDb() {
        return locationDatabase.locationDao().getAllPreferLocations();
    }
}
