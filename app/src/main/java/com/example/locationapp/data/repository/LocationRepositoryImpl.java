package com.example.locationapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.data.sources.model.preferlocation.Root;
import com.example.locationapp.data.sources.model.detaillocation.RootDetail;
import com.example.locationapp.utils.Resource;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public MutableLiveData<Resource<Root>> getAllLocation() {
        if (preferLocation.getValue() != null) {
            preferLocation.setValue(new Resource.Loading<>(null));
        } else {
            preferLocation.setValue(new Resource.Initial<>());
        }

        mLocationAPI.fetchAllLocation().enqueue(new Callback<Root>() {
            @Override
            public void onResponse(@NonNull Call<Root> call, @NonNull Response<Root> response) {
                if (response.body() == null) {
                    preferLocation.setValue(new Resource.Error<>(new Root(response.code(), response.message(), null), null));
                } else if (response.body().getError_code() != 0) {
                    preferLocation.setValue(new Resource.Error<>(response.body(), null));
                } else {
                    preferLocation.setValue(new Resource.Success<>(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
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
                if (response.body() == null) {
                    detailLocation.setValue(new Resource.Error<>(new RootDetail(999, response.message(), null), null));
                } else if (response.body().getError_code() != 0) {
                    detailLocation.setValue(new Resource.Error<>(response.body(), null));
                } else {
                    detailLocation.setValue(new Resource.Success<>(response.body()));
                }
            }

            @Override
            public void onFailure(Call<RootDetail> call, Throwable t) {
                detailLocation.setValue(new Resource.Error<>(new RootDetail(999, t.getMessage(), null), t));
            }
        });
        return detailLocation;
    }

}
