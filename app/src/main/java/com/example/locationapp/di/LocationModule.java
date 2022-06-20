package com.example.locationapp.di;

import com.example.locationapp.data.repository.LocationRepositoryImpl;
import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.domain.interactor.GetDetailLocationUseCase;
import com.example.locationapp.domain.interactor.GetPreferLocationsUseCase;
import com.example.locationapp.domain.repository.LocationRepository;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



@Module
public class LocationModule {

    public final String BASE_URL = "https://run.mocky.io";

    @Provides
    @Singleton
    public OkHttpClient provideClient() {
        return new OkHttpClient.Builder().connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.SECONDS)
                .writeTimeout(1, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    public LocationAPI provideAPI(OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LocationAPI.class);
    }

    @Provides
    @Singleton
    public LocationRepository provideLocationRepository(LocationAPI locationAPI) {
        return new LocationRepositoryImpl(locationAPI);
    }

    @Singleton
    @Provides
    public GetPreferLocationsUseCase provideGetPreferLocationsUseCase(LocationRepository locationRepository) {
        return new GetPreferLocationsUseCase(locationRepository);
    }

    @Singleton
    @Provides
    public GetDetailLocationUseCase provideGetDetailLocationUseCase(LocationRepository locationRepository) {
        return new GetDetailLocationUseCase(locationRepository);
    }
}
