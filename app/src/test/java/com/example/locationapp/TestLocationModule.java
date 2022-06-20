package com.example.locationapp;

import static org.mockito.Mockito.mock;

import com.example.locationapp.data.repository.LocationRepositoryImpl;
import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.di.LocationModule;
import com.example.locationapp.domain.interactor.GetDetailLocationUseCase;
import com.example.locationapp.domain.interactor.GetPreferLocationsUseCase;
import com.example.locationapp.domain.repository.LocationRepository;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestLocationModule extends LocationModule {

    @Override
    public OkHttpClient provideClient() {
        return new OkHttpClient.Builder().connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public LocationAPI provideAPI(OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LocationAPI.class);
    }

    @Override
    public LocationRepository provideLocationRepository(LocationAPI locationAPI) {
        return new LocationRepositoryImpl(locationAPI);
    }

    @Override
    public GetDetailLocationUseCase provideGetDetailLocationUseCase(LocationRepository locationRepository) {
        return new GetDetailLocationUseCase(locationRepository);
    }

    @Override
    public GetPreferLocationsUseCase provideGetPreferLocationsUseCase(LocationRepository locationRepository) {
        return new GetPreferLocationsUseCase(locationRepository);
    }
}
