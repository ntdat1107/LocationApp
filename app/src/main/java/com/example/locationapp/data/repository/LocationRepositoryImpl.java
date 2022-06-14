package com.example.locationapp.data.repository;

import com.example.locationapp.data.sources.remote.Location;
import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.domain.repository.LocationRepository;

import java.util.List;

import javax.inject.Inject;

public class LocationRepositoryImpl implements LocationRepository {

    private LocationAPI mLocationAPI;

    @Inject
    public LocationRepositoryImpl(LocationAPI mLocationAPI) {
        this.mLocationAPI = mLocationAPI;
    }

    @Override
    public List<Location> getAllLocation() {
        return mLocationAPI.fetchAllLocation();
    }
}
