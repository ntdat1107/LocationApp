package com.example.locationapp.di;

import android.app.Application;

import androidx.room.Room;

import com.example.locationapp.data.repository.LocationRepository;
import com.example.locationapp.data.repository.LocationRepositoryImpl;
import com.example.locationapp.data.repository.TestRepository;
import com.example.locationapp.data.repository.TestRepositoryImpl;
import com.example.locationapp.data.sources.local.LocalRepository;
import com.example.locationapp.data.sources.local.LocalRepositoryImpl;
import com.example.locationapp.data.sources.local.LocationDatabase;
import com.example.locationapp.data.sources.remote.LocationAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class LocationModule {

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder().baseUrl("https://run.mocky.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public LocationAPI provideAPI(Retrofit retrofit) {
        return retrofit.create(LocationAPI.class);
    }

    @Provides
    @Singleton
    public LocationRepository provideLocationRepository(LocationAPI locationAPI) {
        return new LocationRepositoryImpl(locationAPI);
    }

    @Provides
    @Singleton
    public LocationDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, LocationDatabase.class, "prefer_locations_database").build();
    }

    @Provides
    @Singleton
    public LocalRepository provideLocalRepository(LocationDatabase locationDatabase) {
        return new LocalRepositoryImpl(locationDatabase);
    }

    @Provides
    @Singleton
    public TestRepository provideTestRepository(LocationRepository locationRepository, LocalRepository localRepository) {
        return new TestRepositoryImpl(locationRepository, localRepository);
    }
}
