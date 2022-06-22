package com.example.locationapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.data.sources.remote.model.preferlocation.Root;
import com.example.locationapp.data.sources.remote.model.detaillocation.RootDetail;
import com.example.locationapp.utils.Resource;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationRepositoryImpl implements LocationRepository {

    private final LocationAPI mLocationAPI;

    private final MutableLiveData<Resource<Root>> preferLocation = new MutableLiveData<>();

    private final MutableLiveData<Resource<RootDetail>> detailLocation = new MutableLiveData<>();

    @Inject
    public LocationRepositoryImpl(LocationAPI mLocationAPI) {
        this.mLocationAPI = mLocationAPI;
    }

    @Override
    public MutableLiveData<Resource<Root>> getAllLocation() {
        if (preferLocation.getValue() != null) {
            preferLocation.setValue(new Resource.Loading<>(null));
        } else {
            preferLocation.setValue(new Resource.Initial<>());
        }
        Call<Root> call = mLocationAPI.fetchAllLocation();
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                preferLocation.setValue(new Resource.Success<>(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<Root> call, @NonNull Throwable t) {
                preferLocation.setValue(new Resource.Error<>(new Root(999, t.getMessage(), null), t));
            }
        });
        return preferLocation;
    }

    @Override
    public MutableLiveData<Resource<RootDetail>> getLocationDetail(String locationID) {
        if (detailLocation.getValue() != null) {
            detailLocation.setValue(new Resource.Loading<>(null));
        } else {
            detailLocation.setValue(new Resource.Initial<>());
        }

        Call<RootDetail> call = mLocationAPI.fetchLocationDetail(locationID);

        call.enqueue(new Callback<RootDetail>() {
            @Override
            public void onResponse(@NonNull Call<RootDetail> call, @NonNull Response<RootDetail> response) {
                detailLocation.setValue(new Resource.Success<>(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<RootDetail> call, @NonNull Throwable t) {
                detailLocation.setValue(new Resource.Error<>(new RootDetail(999, t.getMessage(), null), t));
            }
        });
        return detailLocation;
    }

}
