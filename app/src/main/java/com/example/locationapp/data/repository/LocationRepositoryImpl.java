package com.example.locationapp.data.repository;

import android.util.Log;

import com.example.locationapp.data.sources.remote.Location;
import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.data.sources.remote.Root;
import com.example.locationapp.domain.repository.LocationRepository;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class LocationRepositoryImpl implements LocationRepository {

    private LocationAPI mLocationAPI;

    @Inject
    public LocationRepositoryImpl(LocationAPI mLocationAPI) {
        this.mLocationAPI = mLocationAPI;
    }

    @Override
    public Call<Root> getAllLocation() {
        return mLocationAPI.fetchAllLocation();
    }
}
