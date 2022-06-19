package com.example.locationapp.di;

import com.example.locationapp.presentation.locationdetail.viewmodel.LocationViewModel;
import com.example.locationapp.presentation.locationlist.viewmodel.LocationListViewModel;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {LocationModule.class})
public interface LocationComponent {
    public void inject(LocationListViewModel locationListViewModel);

    public void inject(LocationViewModel locationViewModel);
}
