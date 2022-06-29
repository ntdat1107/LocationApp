package com.example.locationapp.data.sources.local;

import androidx.lifecycle.LiveData;

import com.example.locationapp.data.sources.local.location.LocationDatabase;
import com.example.locationapp.data.sources.local.preferlocations.PreferLocationsDatabase;
import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;
import com.example.locationapp.data.sources.model.preferlocation.Location;

import java.util.List;

import javax.inject.Inject;

public class LocalRepositoryImpl implements LocalRepository {
    private final PreferLocationsDatabase preferLocationsDatabase;
    private final LocationDatabase locationDatabase;

    @Inject
    public LocalRepositoryImpl(PreferLocationsDatabase preferLocationsDatabase, LocationDatabase locationDatabase) {
        this.preferLocationsDatabase = preferLocationsDatabase;
        this.locationDatabase = locationDatabase;
    }


    @Override
    public void insertLocation(Location location) {
        preferLocationsDatabase.locationDao().insertLocation(location);
    }

    @Override
    public LiveData<List<Location>> getAllLocationFromDb() {
        return preferLocationsDatabase.locationDao().getAllPreferLocations();
    }

    @Override
    public void insertDetailLocation(LocationDetail locationDetail) {
        locationDatabase.locationDao().insertLocation(locationDetail);
    }

    @Override
    public LiveData<LocationDetail> getDetailLocationFromDB(String locationID) {
        return locationDatabase.locationDao().getDetailLocations(locationID);
    }
}
