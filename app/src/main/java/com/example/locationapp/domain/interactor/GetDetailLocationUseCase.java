package com.example.locationapp.domain.interactor;

import com.example.locationapp.data.sources.remote.model.RootDetail;
import com.example.locationapp.domain.repository.LocationRepository;

import javax.inject.Inject;

import retrofit2.Call;

public class GetDetailLocationUseCase {
    private LocationRepository locationRepository;
    @Inject
    public GetDetailLocationUseCase(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Call<RootDetail> execute(String locationID) {
        return locationRepository.getLocationDetail(locationID);
    }
}
