package com.example.locationapp.domain.interactor;

import com.example.locationapp.data.sources.remote.model.Root;
import com.example.locationapp.domain.repository.LocationRepository;

import javax.inject.Inject;

import retrofit2.Call;

public class GetPreferLocationsUseCase {
    private LocationRepository locationRepository;

    @Inject
    public GetPreferLocationsUseCase(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Call<Root> execute() {
        return locationRepository.getAllLocation();
    }
}