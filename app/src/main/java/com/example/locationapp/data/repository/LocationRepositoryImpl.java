package com.example.locationapp.data.repository;

import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.data.sources.remote.model.Root;
import com.example.locationapp.data.sources.remote.model.RootDetail;
import com.example.locationapp.domain.repository.LocationRepository;

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

    @Override
    public Call<RootDetail> getLocationDetail(String locationID) {
        return mLocationAPI.fetchLocationDetail(locationID);
    }

}
