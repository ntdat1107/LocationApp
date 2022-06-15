package com.example.locationapp.di;

import android.util.Log;

import com.example.locationapp.data.repository.LocationRepositoryImpl;
import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.domain.repository.LocationRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    @Singleton
    static LocationAPI provideAPI() {
        Log.i("test", "provide api");
        return new Retrofit.Builder().baseUrl("https://run.mocky.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LocationAPI.class);
    }

    @Provides
    @Singleton
    static LocationRepository provideLocationRepository(LocationAPI locationAPI) {
        Log.i("test", "provide repo");
        return new LocationRepositoryImpl(locationAPI);
    }
}
