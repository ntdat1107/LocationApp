package com.example.locationapp.data.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.locationapp.data.sources.local.LocalRepository;
import com.example.locationapp.data.sources.model.detaillocation.LocationDetail;
import com.example.locationapp.data.sources.model.detaillocation.RootDetail;
import com.example.locationapp.data.sources.model.preferlocation.Location;
import com.example.locationapp.data.sources.model.preferlocation.Root;
import com.example.locationapp.data.sources.remote.RemoteRepository;
import com.example.locationapp.utils.AppExecutors;
import com.example.locationapp.utils.NetworkBoundResource;
import com.example.locationapp.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class LocationRepositoryImpl implements LocationRepository {
    private final double timeToLiveInMillis = 5 * 60 * 1000;
    private final RemoteRepository remoteRepository;
    private final LocalRepository localRepository;
    private final AppExecutors appExecutors;

    @Inject
    public LocationRepositoryImpl(RemoteRepository remoteRepository, LocalRepository localRepository, AppExecutors appExecutors) {
        this.remoteRepository = remoteRepository;
        this.localRepository = localRepository;
        this.appExecutors = appExecutors;
    }

    @Override
    public LiveData<Resource<List<Location>>> getPreferLocations() {
        return new NetworkBoundResource<List<Location>, Root>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull Root item) {
                for (Location location : item.getData().getLocations()) {
                    location.setTimestamp(System.currentTimeMillis());
                    localRepository.insertLocation(location);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Location> data) {
                if (data == null || data.size() == 0) {
                    return true;
                }
                return System.currentTimeMillis() - data.get(0).getTimestamp() >= timeToLiveInMillis;
            }

            @NonNull
            @Override
            protected LiveData<List<Location>> loadFromDb() {
                return localRepository.getAllLocationFromDb();
            }

            @NonNull
            @Override
            protected Call<Root> createCall() {
                return remoteRepository.getAllLocation();
            }
        }.getAsLiveData();
    }

    @Override
    public LiveData<Resource<LocationDetail>> getDetailLocation(String locationID) {
        return new NetworkBoundResource<LocationDetail, RootDetail>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull RootDetail item) {
                LocationDetail locationDetail = item.getData().getLocation();
                locationDetail.setId(locationID);
                locationDetail.setTimestamp(System.currentTimeMillis());
                localRepository.insertDetailLocation(locationDetail);
            }

            @Override
            protected boolean shouldFetch(@Nullable LocationDetail data) {
                if (data == null) return true;
                return System.currentTimeMillis() - data.getTimestamp() >= timeToLiveInMillis;
            }

            @NonNull
            @Override
            protected LiveData<LocationDetail> loadFromDb() {
                return localRepository.getDetailLocationFromDB(locationID);
            }

            @NonNull
            @Override
            protected Call<RootDetail> createCall() {
                return remoteRepository.getLocationDetail(locationID);
            }
        }.getAsLiveData();
    }

}
