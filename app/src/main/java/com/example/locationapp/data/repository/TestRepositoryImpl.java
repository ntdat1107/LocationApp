package com.example.locationapp.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.locationapp.data.sources.local.LocalRepository;
import com.example.locationapp.data.sources.model.preferlocation.Location;
import com.example.locationapp.data.sources.model.preferlocation.Root;
import com.example.locationapp.utils.AppExecutors;
import com.example.locationapp.utils.NetworkBoundResource;
import com.example.locationapp.utils.Resource;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;

public class TestRepositoryImpl implements TestRepository {
    private final LocationRepository locationRepository;
    private final LocalRepository localRepository;

    @Inject
    public TestRepositoryImpl(LocationRepository locationRepository, LocalRepository localRepository) {
        this.locationRepository = locationRepository;
        this.localRepository = localRepository;
    }

    @Override
    public LiveData<Resource<List<Location>>> getPreferLocations() {
        return new NetworkBoundResource<List<Location>, Root>(AppExecutors.getInstance()) {

            @Override
            protected void saveCallResult(@NonNull Root item) {
                for (Location location: item.getData().getLocations()) {
                    localRepository.insertLocation(location);
                }
            }

            @NonNull
            @Override
            protected LiveData<List<Location>> loadFromDb() {
                return localRepository.getAllLocationFromDb();
            }

            @NonNull
            @Override
            protected Call<Root> createCall() {
                return locationRepository.getAllLocation();
            }
        }.getAsLiveData();
    }
}
