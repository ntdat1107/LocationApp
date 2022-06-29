package com.example.locationapp.di;

import android.app.Application;

import androidx.room.Room;

import com.example.locationapp.data.repository.LocationRepository;
import com.example.locationapp.data.repository.LocationRepositoryImpl;
import com.example.locationapp.data.sources.local.LocalRepository;
import com.example.locationapp.data.sources.local.LocalRepositoryImpl;
import com.example.locationapp.data.sources.local.location.LocationDatabase;
import com.example.locationapp.data.sources.local.preferlocations.PreferLocationsDatabase;
import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.data.sources.remote.RemoteRepository;
import com.example.locationapp.data.sources.remote.RemoteRepositoryImpl;
import com.example.locationapp.utils.AppExecutors;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;

@Module
@InstallIn(ViewModelComponent.class)
public class ViewModelModule {
    @Provides
    @ViewModelScoped
    public RemoteRepository provideLocationRepository(LocationAPI locationAPI) {
        return new RemoteRepositoryImpl(locationAPI);
    }

    @Provides
    @ViewModelScoped
    public PreferLocationsDatabase providePreferDatabase(Application application) {
        return Room.databaseBuilder(application, PreferLocationsDatabase.class, "prefer_locations_database").build();
    }

    @Provides
    @ViewModelScoped
    public LocationDatabase provideLocationDatabase(Application application) {
        return Room.databaseBuilder(application, LocationDatabase.class, "location_database").build();
    }

    @Provides
    @ViewModelScoped
    public LocalRepository provideLocalRepository(PreferLocationsDatabase preferLocationsDatabase, LocationDatabase locationDatabase) {
        return new LocalRepositoryImpl(preferLocationsDatabase, locationDatabase);
    }

    @Provides
    @ViewModelScoped
    public AppExecutors provideAppExecutors() {
        return AppExecutors.getInstance();
    }

    @Provides
    @ViewModelScoped
    public LocationRepository provideTestRepository(RemoteRepository remoteRepository, LocalRepository localRepository, AppExecutors appExecutors) {
        return new LocationRepositoryImpl(remoteRepository, localRepository, appExecutors);
    }
}
