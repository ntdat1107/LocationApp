package com.example.locationapp;

import static org.mockito.Mockito.mock;

import com.example.locationapp.data.repository.LocationRepositoryImpl;
import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.di.LocationModule;
import com.example.locationapp.domain.interactor.GetDetailLocationUseCase;
import com.example.locationapp.domain.interactor.GetPreferLocationsUseCase;
import com.example.locationapp.domain.repository.LocationRepository;

public class TestLocationModule extends LocationModule {

    @Override
    public LocationAPI provideAPI() {
        return mock(LocationAPI.class);
    }

    @Override
    public LocationRepository provideLocationRepository(LocationAPI locationAPI) {
        return mock(LocationRepositoryImpl.class);
    }

    @Override
    public GetDetailLocationUseCase provideGetDetailLocationUseCase(LocationRepository locationRepository) {
        return mock(GetDetailLocationUseCase.class);
    }

    @Override
    public GetPreferLocationsUseCase provideGetPreferLocationsUseCase(LocationRepository locationRepository) {
        return mock(GetPreferLocationsUseCase.class);
    }
}
