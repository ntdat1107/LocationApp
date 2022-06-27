package com.example.locationapp.di;

import android.app.Application;

import androidx.room.Room;

import com.example.locationapp.data.remote.LocationRepository;
import com.example.locationapp.data.sources.local.location.LocationDatabase;
import com.example.locationapp.data.sources.local.preferlocations.PreferLocationsDatabase;
import com.example.locationapp.data.sources.remote.RemoteRepository;
import com.example.locationapp.data.sources.remote.RemoteRepositoryImpl;
import com.example.locationapp.data.remote.LocationRepositoryImpl;
import com.example.locationapp.data.sources.local.LocalRepository;
import com.example.locationapp.data.sources.local.LocalRepositoryImpl;
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
public class AppModule {

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
    public RemoteRepository provideLocationRepository(LocationAPI locationAPI) {
        return new RemoteRepositoryImpl(locationAPI);
    }

    @Provides
    @Singleton
    public PreferLocationsDatabase providePreferDatabase(Application application) {
        return Room.databaseBuilder(application, PreferLocationsDatabase.class, "prefer_locations_database").build();
    }

    @Provides
    @Singleton
    public LocationDatabase provideLocationDatabase(Application application) {
        return Room.databaseBuilder(application, LocationDatabase.class, "location_database").build();
    }

    @Provides
    @Singleton
    public LocalRepository provideLocalRepository(PreferLocationsDatabase preferLocationsDatabase, LocationDatabase locationDatabase) {
        return new LocalRepositoryImpl(preferLocationsDatabase, locationDatabase);
    }

    @Provides
    @Singleton
    public LocationRepository provideTestRepository(RemoteRepository remoteRepository, LocalRepository localRepository) {
        return new LocationRepositoryImpl(remoteRepository, localRepository);
    }
}
