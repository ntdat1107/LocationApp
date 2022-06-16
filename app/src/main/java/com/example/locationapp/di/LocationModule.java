package com.example.locationapp.di;

import com.example.locationapp.data.repository.LocationRepositoryImpl;
import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.domain.interactor.GetDetailLocationUseCase;
import com.example.locationapp.domain.interactor.GetPreferLocationsUseCase;
import com.example.locationapp.domain.repository.LocationRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class LocationModule {

    @Provides
    @Singleton
    static LocationAPI provideAPI() {
        return new Retrofit.Builder().baseUrl("https://run.mocky.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LocationAPI.class);
    }

    @Provides
    @Singleton
    static LocationRepository provideLocationRepository(LocationAPI locationAPI) {
        return new LocationRepositoryImpl(locationAPI);
    }

    @Singleton
    @Provides
    static GetPreferLocationsUseCase provideGetPreferLocationsUseCase(LocationRepository locationRepository) {
        return new GetPreferLocationsUseCase(locationRepository);
    }

    @Singleton
    @Provides
    static GetDetailLocationUseCase provideGetDetailLocationUseCase(LocationRepository locationRepository) {
        return new GetDetailLocationUseCase(locationRepository);
    }
}
