package com.example.locationapp.di;

import com.example.locationapp.presentation.locationdetail.LocationViewModel;
import com.example.locationapp.presentation.locationlist.LocationListViewModel;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface LocationComponent {
    public void inject(LocationListViewModel locationListViewModel);

    public void inject(LocationViewModel locationViewModel);
}
