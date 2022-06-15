package com.example.locationapp.di;

import com.example.locationapp.data.sources.remote.LocationAPI;
import com.example.locationapp.domain.repository.LocationRepository;
import com.example.locationapp.presentation.LocationListViewModel;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface LocationComponent {
    public void inject(LocationListViewModel locationListViewModel);
}
