package com.example.locationapp.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.data.sources.model.preferlocation.Root;
import com.example.locationapp.data.sources.model.detaillocation.RootDetail;
import com.example.locationapp.utils.Resource;

import javax.inject.Inject;

public class LocationRepositoryImpl implements LocationRepository {

    private final LocationAPI mLocationAPI;

    private final MutableLiveData<Resource<Root>> preferLocation;

    private final MutableLiveData<Resource<RootDetail>> detailLocation;

    @Inject
    public LocationRepositoryImpl(LocationAPI mLocationAPI) {
        this.mLocationAPI = mLocationAPI;
        preferLocation = new MutableLiveData<>();
        detailLocation = new MutableLiveData<>();
    }

    @Override
    public LiveData<Root> getAllLocation() {
        return mLocationAPI.fetchAllLocation();
    }

    @Override
    public LiveData<RootDetail> getLocationDetail(String locationID) {
        return mLocationAPI.fetchLocationDetail(locationID);
    }

}
