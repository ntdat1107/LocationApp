package com.example.locationapp.data.sources.remote;

import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.data.sources.model.preferlocation.Root;
import com.example.locationapp.data.sources.model.detaillocation.RootDetail;
import com.example.locationapp.utils.Resource;

import javax.inject.Inject;

import retrofit2.Call;

public class RemoteRepositoryImpl implements RemoteRepository {

    private final LocationAPI mLocationAPI;

    private final MutableLiveData<Resource<Root>> preferLocation;

    private final MutableLiveData<Resource<RootDetail>> detailLocation;

    @Inject
    public RemoteRepositoryImpl(LocationAPI mLocationAPI) {
        this.mLocationAPI = mLocationAPI;
        preferLocation = new MutableLiveData<>();
        detailLocation = new MutableLiveData<>();
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
